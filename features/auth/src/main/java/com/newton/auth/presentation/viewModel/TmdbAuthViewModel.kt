package com.newton.auth.presentation.viewModel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.manager.TmdbCustomTabsManager
import com.newton.core.enums.ErrorType
import com.newton.domain.models.*
import com.newton.domain.repository.TmdbAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TmdbAuthViewModel @Inject constructor(
    private val tmdbAuthRepository: TmdbAuthRepository,
    private val customTabsManager: TmdbCustomTabsManager
) : ViewModel() {

    private val _authState = MutableStateFlow(TmdbAuthState())
    val authState: StateFlow<TmdbAuthState> = _authState.asStateFlow()

    private var currentRequestToken: String? = null

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        val user = tmdbAuthRepository.getCurrentUser()
        val sessionId = tmdbAuthRepository.getCurrentSessionId()

        _authState.update {
            it.copy(
                isAuthenticated = user != null && sessionId != null,
                user = user,
                sessionId = sessionId
            )
        }
    }

    suspend fun startAuthentication(onIntentReady: (Intent?) -> Unit) {
        _authState.update { it.copy(isLoading = true, error = null) }

        tmdbAuthRepository.createRequestToken().collect { result ->
            result.handle(
                onLoading = { isLoading ->
                    _authState.update { it.copy(isLoading = isLoading) }
                },
                onSuccess = { requestToken ->
                    if (requestToken != null) {
                        currentRequestToken = requestToken.requestToken
                        val intent = customTabsManager.launchTmdbAuth(requestToken.requestToken)
                        onIntentReady(intent)
                    } else {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                error = "Failed to create request token"
                            )
                        }
                        onIntentReady(null)
                    }
                },
                onError = { error, errorType, _ ->
                    Timber.e("Failed to create request token: $error")
                    _authState.update {
                        it.copy(
                            isLoading = false,
                            error = error ?: "Failed to start authentication"
                        )
                    }
                    onIntentReady(null)
                }
            )
        }
    }

    suspend fun completeAuthentication() {
        val requestToken = currentRequestToken
        if (requestToken == null) {
            _authState.update {
                it.copy(
                    isLoading = false,
                    error = "No request token available"
                )
            }
            return
        }

        _authState.update { it.copy(isLoading = true, error = null) }

        // Create session from approved request token
        tmdbAuthRepository.createSession(requestToken).collect { sessionResult ->
            sessionResult.handle(
                onLoading = { isLoading ->
                    _authState.update { it.copy(isLoading = isLoading) }
                },
                onSuccess = { session ->
                    if (session != null && session.success) {
                        // Get user account details
                        fetchUserDetails(session.sessionId)
                    } else {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                error = "Failed to create session"
                            )
                        }
                    }
                },
                onError = { error, errorType, _ ->
                    Timber.e("Failed to create session: $error")
                    _authState.update {
                        it.copy(
                            isLoading = false,
                            error = error ?: "Failed to complete authentication"
                        )
                    }
                }
            )
        }
    }

    private suspend fun fetchUserDetails(sessionId: String) {
        tmdbAuthRepository.getAccountDetails(sessionId).collect { userResult ->
            userResult.handle(
                onLoading = { isLoading ->
                    _authState.update { it.copy(isLoading = isLoading) }
                },
                onSuccess = { user ->
                    if (user != null) {
                        // Save user data and session
                        tmdbAuthRepository.saveUserData(user, sessionId)

                        _authState.update {
                            it.copy(
                                isAuthenticated = true,
                                user = user,
                                sessionId = sessionId,
                                isLoading = false,
                                error = null
                            )
                        }

                        currentRequestToken = null
                        Timber.d("Authentication completed successfully for user: ${user.username}")
                    } else {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                error = "Failed to get user details"
                            )
                        }
                    }
                },
                onError = { error, errorType, _ ->
                    Timber.e("Failed to get user details: $error")
                    _authState.update {
                        it.copy(
                            isLoading = false,
                            error = error ?: "Failed to get user details"
                        )
                    }
                }
            )
        }
    }

    fun signOut() {
        viewModelScope.launch {
            val sessionId = tmdbAuthRepository.getCurrentSessionId()

            if (sessionId != null) {
                tmdbAuthRepository.deleteSession(sessionId).collect { result ->
                    result.handle(
                        onSuccess = {
                            clearUserData()
                        },
                        onError = { error, _, _ ->
                            Timber.w("Failed to delete session on server: $error")
                            // Still clear local data even if server deletion fails
                            clearUserData()
                        }
                    )
                }
            } else {
                clearUserData()
            }
        }
    }

    private suspend fun clearUserData() {
        tmdbAuthRepository.clearUserData()
        _authState.update {
            TmdbAuthState(
                isAuthenticated = false,
                user = null,
                sessionId = null,
                isLoading = false,
                error = null
            )
        }
        currentRequestToken = null
    }

    fun clearError() {
        _authState.update { it.copy(error = null) }
    }

    override fun onCleared() {
        super.onCleared()
        customTabsManager.unbindService()
    }
}