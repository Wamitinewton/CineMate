package com.newton.network.data.dto
import kotlinx.serialization.Serializable


@Serializable
data class AccountDetailsDto(
    val avatar: AvatarDto?,
    val id: Int,
    val iso_639_1: String,
    val iso_3166_1: String,
    val name: String,
    val include_adult: Boolean,
    val username: String
)

@Serializable
data class AvatarDto(
    val gravatar: GravatarDto?,
    val tmdb: TmdbAvatarDto?
)

@Serializable
data class GravatarDto(
    val hash: String?
)

@Serializable
data class TmdbAvatarDto(
    val avatar_path: String?
)
