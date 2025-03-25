package com.newton.network.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AllTrendingDto(
    val page: Int,
    val results: List<AllTrendingDtoResults>,
    val total_pages: Int,
    val total_results: Int
)


@Serializable
data class AllTrendingDtoResults(
    val adult: Boolean,
    val backdrop_path: String?,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val media_type: String,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)