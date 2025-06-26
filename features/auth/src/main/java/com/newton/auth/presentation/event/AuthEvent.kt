package com.newton.auth.presentation.event

/**
 * Events that can be triggered from the UI
 */
sealed class AuthEvent {
    /**
     * Dismiss current error
     */
    object DismissError : AuthEvent()

    /**
     * User clicked Google sign-in button
     */
    object OnGoogleSignInClick : AuthEvent()

    /**
     * Sign out user
     */
    object SignOut : AuthEvent()
}

/**
 * Events emitted from ViewModel to UI
 */
sealed class AuthUiEvent {
    /**
     * Show error message
     */
    data class ShowError(val message: String) : AuthUiEvent()

    /**
     * Navigation events
     */
    object NavigateToHome : AuthUiEvent()
}