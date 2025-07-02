package com.newton.network.data.dto

@kotlinx.serialization.Serializable
data class DeleteSessionRequest(
    val session_id: String
)