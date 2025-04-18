package com.newton.network.data.dto

data class PeopleResponseDto(
    val adult: Boolean?,
    val id: Int,
    val name: String?,
    val original_name: String?,
    val profile_path: String?
)
