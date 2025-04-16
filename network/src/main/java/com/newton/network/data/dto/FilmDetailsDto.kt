package com.newton.network.data.dto

import kotlinx.serialization.*

@Serializable
data class FilmDetailsDto(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: String?,
    val budget: Int?,
    val genres: List<GenreDto>?,
    val homepage: String?,
    val id: Int?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val production_companies: List<ProductionCompanyDto>?,
    val production_countries: List<ProductionCountryDto>?,
    val release_date: String?,
    val revenue: Int?,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguageDto>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?,
    val created_by: List<CreatedByDto>?,
    val episode_run_time: List<Int>?,
    val first_air_date: String?,
    val in_production: Boolean?,
    val languages: List<String>?,
    val last_air_date: String?,
    val last_episode_to_air: LastOrNextToAirDto?,
    val name: String?,
    val networks: List<NetworkDto>?,
    val next_episode_to_air: LastOrNextToAirDto?,
    val number_of_episodes: Int?,
    val number_of_seasons: Int?,
    val origin_country: List<String>?,
    val original_name: String?,
    val seasons: List<SeasonDto>?,
    val type: String?,
    )

@Serializable
data class GenreDto(
    val id: Int?,
    val name: String?
)

@Serializable
data class ProductionCompanyDto(
    val id: Int?,
    val logo_path: String?,
    val name: String?,
    val origin_country: String?
)

@Serializable
data class ProductionCountryDto(
    val iso_3166_1: String?,
    val name: String?
)

@Serializable
data class SpokenLanguageDto(
    val english_name: String?,
    val iso_639_1: String?,
    val name: String?
)

@Serializable
data class CreatedByDto(
    val credit_id: String?,
    val gender: Int?,
    val id: Int?,
    val name: String?,
    val profile_path: String?
)

@Serializable
data class LastOrNextToAirDto(
    val air_date: String?,
    val episode_number: Int?,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val production_code: String?,
    val runtime: Int?,
    val season_number: Int?,
    val show_id: Int?,
    val still_path: String?,
    val vote_average: Double?,
    val vote_count: Int?
)

@Serializable
data class NetworkDto(
    val id: Int?,
    val logo_path: String?,
    val name: String?,
    val origin_country: String?
)


@Serializable
data class SeasonDto(
    val air_date: String?,
    val episode_count: Int?,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val poster_path: String?,
    val season_number: Int?,
    val vote_average: Double?
)