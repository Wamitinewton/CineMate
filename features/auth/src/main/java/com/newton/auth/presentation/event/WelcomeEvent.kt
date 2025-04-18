package com.newton.auth.presentation.event

sealed class WelcomeEvent {
    data class ToggleAdultContent(val allow: Boolean) : WelcomeEvent()
    data object Continue : WelcomeEvent()
}

sealed class WelcomeNavigationEvent {
    data object NavigateToHome : WelcomeNavigationEvent()
}