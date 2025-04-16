package com.newton.network.data.mappers

import com.newton.core.utils.*
import com.newton.domain.models.FilmData
import com.newton.network.data.dto.*

fun FilmResponseDto.toAllTrendingDomain(): FilmData {
    return FilmData(
        adult = adult,
        backdropPath = backdrop_path?.let { "${ImageBaseUrl.IMAGE_BASE_URL}$it" },
        firstAirDate = first_air_date,
        genreIds = genre_ids,
        id = id,
        mediaType = media_type,
        name = name,
        originCountry = origin_country,
        originalLanguage = original_language,
        originalName = original_name,
        originalTitle = original_title,
        overview = overview,
        popularity = popularity,
        posterPath = poster_path?.let { "${ImageBaseUrl.IMAGE_BASE_URL}$it" },
        releaseDate = release_date,
        title = title,
        video = video,
        voteAverage = vote_average,
        voteCount = vote_count
    )
}

fun List<FilmResponseDto>.toAllTrendingDomainList(): List<FilmData> =
    map { it.toAllTrendingDomain() }