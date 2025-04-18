package com.newton.domain.models

import kotlinx.serialization.*

data class FilmDetails(
    val adult: Boolean,
    val backdropPath: String?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int?,
    val imdbId: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompany>?,
    val productionCountries: List<ProductionCountry>?,
    val releaseDate: String?,
    val revenue: Int?,
    val runtime: Int?,
    val spokenLanguages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val createdBy: List<CreatedBy>?,
    val episodeTime: List<Int>?,
    val firstAirDate: String?,
    val inProduction: Boolean?,
    val languages: List<String>?,
    val lastAirDate: String?,
    val lastEpisodeToAir: LastOrNextToAir?,
    val name: String?,
    val networks: List<Network>?,
    val nextEpisodeToAir: LastOrNextToAir?,
    val numberOfEpisodes: Int?,
    val numberOfSeasons: Int?,
    val originCountry: List<String>?,
    val originalName: String?,
    val seasons: List<Season>?,
    val type: String?,
)

data class Genre(
    val id: Int?,
    val name: String?
)

data class ProductionCompany(
    val id: Int?,
    val logoPath: String?,
    val name: String?,
    val originCountry: String?
)

data class ProductionCountry(
    val iso31661: String?,
    val name: String?
)

data class SpokenLanguage(
    val englishName: String?,
    val iso6391: String?,
    val name: String?
)

@Serializable
data class CreatedBy(
    val creditId: String?,
    val gender: Int?,
    val id: Int?,
    val name: String?,
    val profilePath: String?
)

@Serializable
data class LastOrNextToAir(
    val airDate: String?,
    val episodeNumber: Int?,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val productionCode: String?,
    val runtime: Int?,
    val seasonNumber: Int?,
    val showId: Int?,
    val stillPath: String?,
    val voteAverage: Double?,
    val voteCount: Int?
)

data class Network(
    val id: Int?,
    val logoPath: String?,
    val name: String?,
    val originCountry: String?
)


data class Season(
    val airDate: String?,
    val episodeCount: Int?,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val posterPath: String?,
    val seasonNumber: Int?,
    val voteAverage: Double?
)
