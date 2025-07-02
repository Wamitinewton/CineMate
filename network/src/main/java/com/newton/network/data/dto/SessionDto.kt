package com.newton.network.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
    val success: Boolean,
    val session_id: String
)
