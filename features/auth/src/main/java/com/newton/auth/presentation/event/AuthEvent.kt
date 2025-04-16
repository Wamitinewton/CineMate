package com.newton.auth.presentation.event

import androidx.credentials.*


/**
 * Events that can be triggered from the UI
 */
sealed class AuthEvent {
    /**
     * Dismiss current error
     */
    data object DismissError : AuthEvent()

    /**
     * User clicked login button
     */
    data class OnLoginClick(val webClientId: String) : AuthEvent()

    /**
     * Sign in with Google ID token
     */
    data class SignInWithGoogle(val idToken: String) : AuthEvent()
}

/**
 * Events emitted from ViewModel to UI
 */
sealed class AuthUiEvent {
    /**
     * Launch Credential Manager with the provided request
     */
    data class LaunchCredentialManager(val request: GetCredentialRequest) : AuthUiEvent()
}