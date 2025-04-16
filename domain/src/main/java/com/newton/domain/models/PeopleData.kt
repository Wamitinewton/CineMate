package com.newton.domain.models

data class PeopleData(
    val adult: Boolean?,
    val gender: Int?,
    val id: Int,
    val knownFor: List<FilmData>?,
    val department: String?,
    val mediaType: String?,
    val name: String?,
    val originalName: String?,
    val popularity: Double?,
    val profilePath: String?
)

