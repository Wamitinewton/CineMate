package com.newton.auth.presentation.viewModel

import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.manager.GoogleAuthUiClient
import com.newton.auth.presentation.manager.SignInResult
import com.newton.auth.presentation.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        val currentUser = googleAuthUiClient.getSignedInUser()
        _state.update {
            it.copy(
                isAuthenticated = currentUser != null,
                user = currentUser?.let { user ->
                    com.newton.domain.models.User(
                        uid = user.userId,
                        email = user.email,
                        displayName = user.userName,
                        photoUrl = user.profilePictureUrl
                    )
                }
            )
        }
    }

    suspend fun signIn(): IntentSender? {
        return try {
            _state.update { it.copy(isLoading = true, error = null) }
            googleAuthUiClient.signIn()
        } catch (e: Exception) {
            Timber.e(e, "Sign in failed")
            _state.update {
                it.copy(
                    isLoading = false,
                    error = "Failed to start sign in: ${e.message}"
                )
            }
            null
        }
    }

    // New method to handle the Intent directly
    fun handleSignInIntent(intent: Intent) {
        viewModelScope.launch {
            try {
                val signInResult = googleAuthUiClient.signInWithIntent(intent)
                handleSignInResult(signInResult)
            } catch (e: Exception) {
                Timber.e(e, "Failed to handle sign in intent")
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to complete sign in: ${e.message}"
                    )
                }
            }
        }
    }

    fun handleSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isLoading = false,
                isAuthenticated = result.data != null,
                user = result.data?.let { userData ->
                    com.newton.domain.models.User(
                        uid = userData.userId,
                        email = userData.email,
                        displayName = userData.userName,
                        photoUrl = userData.profilePictureUrl
                    )
                },
                error = result.errorMessage
            )
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                googleAuthUiClient.signOut()
                _state.update {
                    it.copy(
                        isAuthenticated = false,
                        user = null,
                        error = null
                    )
                }
            } catch (e: Exception) {
                Timber.e(e, "Sign out failed")
                _state.update { it.copy(error = "Sign out failed: ${e.message}") }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}