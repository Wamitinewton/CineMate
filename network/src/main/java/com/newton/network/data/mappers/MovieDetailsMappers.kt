package com.newton.network.data.mappers

import com.newton.core.utils.*
import com.newton.network.data.dto.*
import com.newton.network.data.dto.GenreDto
import com.newton.network.domain.models.*

fun MovieResponseDto.toDomainMovieDetails():MovieDetails {
    return MovieDetails(
        adult = adult,
        backdropPath = backdrop_path?.let { "${ImageBaseUrl.IMAGE_BASE_URL}$it" },
        belongsToCollection = belongs_to_collection,
        budget = budget,
        genres = genres?.toDomainGenreList(),
        homepage = homepage,
        id = id,
        imdbId = imdb_id,
        originalLanguage = original_language,
        originalTitle = original_title,
        overview = overview,
        popularity = popularity,
        posterPath = poster_path?.let { "${ImageBaseUrl.IMAGE_BASE_URL}$it" },
        productionCompanies = production_companies?.toDomainProductionCompanyList(),
        productionCountries = production_countries?.toDomainProductionCountryList(),
        releaseDate = release_date,
        revenue = revenue,
        runtime = runtime,
        spokenLanguages = spoken_languages?.toDomainSpokenLanguageList(),
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = vote_average,
        voteCount = vote_count
    )
}

fun GenreDto.toDomainGenre(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun ProductionCompanyDto.toDomainProductionCompany(): ProductionCompany {
    return ProductionCompany(
        id = id,
        logoPath = logo_path.let { "${ImageBaseUrl.IMAGE_BASE_URL}$it" },
        name = name,
        originCountry = origin_country
    )
}

fun ProductionCountryDto.toDomainProductionCountry(): ProductionCountry {
    return ProductionCountry(
        name = name,
        iso31661 = iso_3166_1,
    )
}

fun SpokenLanguageDto.toSpokenLanguageDomain(): SpokenLanguage {
    return SpokenLanguage(
        englishName = english_name,
        iso6391 = iso_639_1,
        name = name
    )
}

fun List<SpokenLanguageDto>.toDomainSpokenLanguageList(): List<SpokenLanguage> =
    map { it.toSpokenLanguageDomain() }

fun List<ProductionCountryDto>.toDomainProductionCountryList(): List<ProductionCountry> =
    map { it.toDomainProductionCountry() }

fun List<ProductionCompanyDto>.toDomainProductionCompanyList(): List<ProductionCompany> =
    map { it.toDomainProductionCompany() }


fun List<GenreDto>.toDomainGenreList(): List<Genre> =
    map { it.toDomainGenre() }

