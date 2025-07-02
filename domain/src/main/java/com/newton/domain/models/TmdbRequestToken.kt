package com.newton.domain.models

data class TmdbRequestToken(
    val success: Boolean,
    val expiresAt: String,
    val requestToken: String
)
