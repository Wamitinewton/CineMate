package com.newton.network.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthErrorDto(
    val success: Boolean,
    val status_code: Int,
    val status_message: String
)
