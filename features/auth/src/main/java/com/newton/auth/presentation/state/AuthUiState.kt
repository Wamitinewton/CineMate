package com.newton.auth.presentation.state

import com.newton.core.enums.*
import com.newton.domain.models.User

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val isAuthenticated: Boolean = false,
    val error: String? = null,
    val errorType: ErrorType? = null
)
