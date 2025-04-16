package com.newton.auth.presentation.viewModel

import androidx.credentials.*
import androidx.credentials.exceptions.*
import androidx.lifecycle.*
import com.newton.auth.presentation.event.*
import com.newton.auth.presentation.manager.*
import com.newton.auth.presentation.state.*
import com.newton.core.enums.*
import com.newton.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.*
import javax.inject.*

/**
 * ViewModel responsible for managing authentication state and operations
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val googleClientManager: GoogleClientManager
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    private val _authEvents = MutableSharedFlow<AuthUiEvent>(extraBufferCapacity = 1)
    val authEvents: SharedFlow<AuthUiEvent> = _authEvents.asSharedFlow()

    init {
        checkAuthState()
    }

    /**
     * Handle UI events
     */
    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.DismissError -> {
                _state.update { it.copy(error = null, errorType = null) }
            }

            is AuthEvent.OnLoginClick -> {
                initiateGoogleSignIn(event.webClientId)
            }

            is AuthEvent.SignInWithGoogle -> {
                signInWithGoogle(event.idToken)
            }
        }
    }

    /**
     * Check initial authentication state
     */
    private fun checkAuthState() {
        val isAuthenticated = authRepository.isUserAuthenticated()
        val currentUser = authRepository.getCurrentUser()
        _state.update {
            it.copy(
                isAuthenticated = isAuthenticated,
                user = currentUser
            )
        }
    }

    /**
     * Initiate Google Sign-In flow
     */
    private fun initiateGoogleSignIn(webClientId: String) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }

                val request = googleClientManager.createGoogleSignInRequest(webClientId)
                Timber.d("Initiating Google Sign-In")

                _authEvents.emit(AuthUiEvent.LaunchCredentialManager(request))
            } catch (e: Exception) {
                Timber.e(e, "Failed to begin sign in")
                handleError("Failed to begin sign in", e)
            }
        }
    }

    /**
     * Process the credential result from Google Sign-In
     */
    fun handleCredentialResult(response: GetCredentialResponse) {
        viewModelScope.launch {
            googleClientManager.processCredentialResponse(response).collect { result ->
                when (result) {
                    is GoogleSignInResult.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is GoogleSignInResult.Success -> {
                        Timber.d("Successfully got Google credential, signing in with token")
                        signInWithGoogle(result.idToken)
                    }

                    is GoogleSignInResult.Error -> {
                        handleError(result.message, result.exception)
                    }
                }
            }
        }
    }

    /**
     * Handle credential error
     */
    fun handleCredentialError(exception: GetCredentialException) {
        viewModelScope.launch {
            googleClientManager.handleCredentialError(exception).collect { result ->
                if (result is GoogleSignInResult.Error) {
                    handleError(result.message, result.exception)
                }
            }
        }
    }

    /**
     * Sign in with Google ID token
     */
    private fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            Timber.d("Signing in with Google token")
            authRepository.signInWithGoogle(idToken).collect { result ->
                result.handle(
                    onLoading = { isLoading ->
                        _state.update { it.copy(isLoading = isLoading) }
                    },
                    onSuccess = { user ->
                        _state.update {
                            it.copy(
                                user = user,
                                isAuthenticated = true,
                                error = null,
                                errorType = null,
                                isLoading = false
                            )
                        }
                    },
                    onError = { message, errorType, _ ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorType = errorType,
                                error = message
                            )
                        }
                    }
                )
            }
        }
    }

    /**
     * Handle authentication errors
     */
    private fun handleError(message: String, exception: Exception? = null) {
        Timber.e(exception, "Auth error: $message")
        _state.update {
            it.copy(
                isLoading = false,
                error = exception?.localizedMessage ?: message,
                errorType = ErrorType.AUTHENTICATION
            )
        }
    }
}