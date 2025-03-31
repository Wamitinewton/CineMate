package com.newton.network.data.dto

data class PeopleResponseDto(
    val adult: Boolean?,
    val gender: Int?,
    val id: Int,
    val known_for: List<FilmResponseDto>?,
    val known_for_department: String?,
    val media_type: String?,
    val name: String?,
    val original_name: String?,
    val popularity: Double?,
    val profile_path: String?
)
