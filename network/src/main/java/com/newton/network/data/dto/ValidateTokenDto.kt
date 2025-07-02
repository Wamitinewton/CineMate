package com.newton.network.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ValidateTokenDto(
    val success: Boolean,
    val expires_at: String,
    val request_token: String
)
