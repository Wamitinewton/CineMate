package com.newton.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class TmdbUser(
    val id: Int,
    val username: String,
    val name: String,
    val avatarPath: String?,
    val includeAdult: Boolean,
    val language: String,
    val country: String
)