package com.newton.auth.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.event.AuthEvent
import com.newton.auth.presentation.event.AuthUiEvent
import com.newton.auth.presentation.manager.GoogleClientManager
import com.newton.auth.presentation.manager.GoogleSignInResult
import com.newton.auth.presentation.state.AuthUiState
import com.newton.domain.models.User
import com.newton.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authRepository: AuthRepository,
    private val googleSignInManager: GoogleClientManager
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    private val _authEvents = MutableSharedFlow<AuthUiEvent>(extraBufferCapacity = 1)
    val authEvents: SharedFlow<AuthUiEvent> = _authEvents.asSharedFlow()

    init {
        initializeGoogleSignIn()
        checkAuthState()
    }

    private fun initializeGoogleSignIn() {
        googleSignInManager.initializeGoogleSignIn(context)
    }

    private fun checkAuthState() {
        val isAuthenticated = authRepository.isUserAuthenticated()
        val currentUser = authRepository.getCurrentUser()
        _state.update {
            it.copy(
                isAuthenticated = isAuthenticated,
                user = currentUser
            )
        }
        Timber.d("Auth state checked - Authenticated: $isAuthenticated")
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.DismissError -> {
                _state.update { it.copy(error = null) }
            }

            is AuthEvent.OnGoogleSignInClick -> {
                performGoogleSignIn()
            }

            is AuthEvent.SignOut -> {
                signOut()
            }

            else -> {
            }
        }
    }

    private fun performGoogleSignIn() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, error = null) }

                googleSignInManager.performGoogleSignIn(context).collect { result ->
                    when (result) {
                        is GoogleSignInResult.Loading -> {
                            _state.update { it.copy(isLoading = true, error = null) }
                        }

                        is GoogleSignInResult.Success -> {
                            val user = result.user
                            if (user != null) {
                                // Save user data to Firestore
                                val domainUser = User(
                                    uid = user.uid,
                                    email = user.email,
                                    displayName = user.displayName,
                                    photoUrl = user.photoUrl?.toString()
                                )

                                try {
                                    authRepository.saveUserData(domainUser)
                                    _state.update {
                                        it.copy(
                                            isLoading = false,
                                            isAuthenticated = true,
                                            user = domainUser,
                                            error = null
                                        )
                                    }
                                    _authEvents.emit(AuthUiEvent.NavigateToHome)
                                } catch (e: Exception) {
                                    Timber.e(e, "Failed to save user data")
                                    _state.update {
                                        it.copy(
                                            isLoading = false,
                                            error = "Failed to save user data: ${e.message}"
                                        )
                                    }
                                }
                            } else {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = "Sign-in successful but no user data received"
                                    )
                                }
                            }
                        }

                        is GoogleSignInResult.Error -> {
                            Timber.e("Google Sign-In failed: ${result.message}")
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.message
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to initiate Google Sign-In")
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to start sign-in: ${e.message}"
                    )
                }
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            googleSignInManager.signOut().collect { result ->
                when (result) {
                    is GoogleSignInResult.Success -> {
                        _state.update {
                            it.copy(
                                isAuthenticated = false,
                                user = null,
                                error = null
                            )
                        }
                    }
                    is GoogleSignInResult.Error -> {
                        _state.update { it.copy(error = result.message) }
                    }
                    else -> {}
                }
            }
        }
    }
}