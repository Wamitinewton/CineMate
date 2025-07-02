package com.newton.auth.presentation.state


import com.newton.core.enums.ErrorType
import com.newton.domain.models.TmdbUser

data class TmdbAuthUiState(
    val isLoading: Boolean = false,
    val user: TmdbUser? = null,
    val isAuthenticated: Boolean = false,
    val error: String? = null,
    val errorType: ErrorType? = null
)