package com.newton.domain.models

data class TmdbAuthState(
    val isAuthenticated: Boolean = false,
    val user: TmdbUser? = null,
    val sessionId: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)