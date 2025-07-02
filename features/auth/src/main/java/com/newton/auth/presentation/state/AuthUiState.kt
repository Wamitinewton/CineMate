package com.newton.auth.presentation.state

import com.newton.core.enums.*

data class AuthUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val error: String? = null,
    val errorType: ErrorType? = null
)
