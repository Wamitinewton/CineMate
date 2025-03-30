package com.newton.network.domain.models

data class User(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val photoUrl: String?
)
