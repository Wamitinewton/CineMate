package com.newton.network.data.mappers

import com.newton.core.utils.*
import com.newton.domain.models.CreatedBy
import com.newton.domain.models.FilmDetails
import com.newton.domain.models.Genre
import com.newton.domain.models.LastOrNextToAir
import com.newton.domain.models.Network
import com.newton.domain.models.ProductionCompany
import com.newton.domain.models.ProductionCountry
import com.newton.domain.models.Season
import com.newton.domain.models.SpokenLanguage
import com.newton.network.data.dto.*

fun FilmDetailsDto.toFilmDomainDetails(): FilmDetails {
    return FilmDetails(
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
        voteCount = vote_count,
        createdBy = created_by?.toCreatedByList(),
        episodeTime = episode_run_time,
        firstAirDate = first_air_date,
        inProduction = in_production,
        languages = languages,
        lastAirDate = last_air_date,
        lastEpisodeToAir = last_episode_to_air?.toLastOrNextToAirDomain(),
        name = name,
        networks = networks?.toNetworkList(),
        nextEpisodeToAir = next_episode_to_air?.toLastOrNextToAirDomain(),
        numberOfEpisodes = number_of_episodes,
        numberOfSeasons = number_of_seasons,
        originCountry = origin_country,
        originalName = original_name,
        seasons = seasons?.toSeasonsList(),
        type = type
    )
}

fun GenreDto.toDomainGenre(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun CreatedByDto.toCreatedByDomain(): CreatedBy {
    return CreatedBy(
        creditId = credit_id,
        gender = gender,
        id = id,
        name = name,
        profilePath = profile_path.let { "${ImageBaseUrl.IMAGE_BASE_URL}$it" }
    )
}

fun LastOrNextToAirDto.toLastOrNextToAirDomain(): LastOrNextToAir {
    return LastOrNextToAir(
        airDate = air_date,
        episodeNumber = episode_number,
        id = id,
        name = name,
        overview = overview,
        productionCode = production_code,
        runtime = runtime,
        seasonNumber = season_number,
        showId = show_id,
        stillPath = still_path.let { "${ImageBaseUrl.IMAGE_BASE_URL}$it" },
        voteAverage = vote_average,
        voteCount = vote_count
    )
}

fun NetworkDto.toDomainNetwork(): Network {
    return Network(
        id = id,
        logoPath = logo_path.let { "${ImageBaseUrl.IMAGE_BASE_URL}$it" },
        name = name,
        originCountry = origin_country
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

fun SeasonDto.toDomainSeasons(): Season {
    return Season(
        airDate = air_date,
        episodeCount = episode_count,
        id = id,
        name = name,
        overview = overview,
        posterPath = poster_path.let { "${ImageBaseUrl.IMAGE_BASE_URL}$it" },
        seasonNumber = season_number,
        voteAverage = vote_average
    )
}

fun List<SeasonDto>.toSeasonsList(): List<Season> =
    map { it.toDomainSeasons() }

fun List<NetworkDto>.toNetworkList(): List<Network> =
    map { it.toDomainNetwork() }

fun List<CreatedByDto>.toCreatedByList(): List<CreatedBy> =
    map { it.toCreatedByDomain() }

fun List<SpokenLanguageDto>.toDomainSpokenLanguageList(): List<SpokenLanguage> =
    map { it.toSpokenLanguageDomain() }

fun List<ProductionCountryDto>.toDomainProductionCountryList(): List<ProductionCountry> =
    map { it.toDomainProductionCountry() }

fun List<ProductionCompanyDto>.toDomainProductionCompanyList(): List<ProductionCompany> =
    map { it.toDomainProductionCompany() }


fun List<GenreDto>.toDomainGenreList(): List<Genre> =
    map { it.toDomainGenre() }

