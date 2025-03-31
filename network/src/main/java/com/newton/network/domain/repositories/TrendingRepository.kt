package com.newton.network.domain.repositories

import androidx.paging.PagingData
import com.newton.network.domain.models.FilmData
import com.newton.network.domain.models.PeopleData
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {
    fun getTrendingShows(allowAdult: Boolean) : Flow<PagingData<FilmData>>

    fun getTrendingPeople() : Flow<PagingData<PeopleData>>

    fun getAllTrendingFilms() : Flow<PagingData<FilmData>>

    fun getTrendingMovies() : Flow<PagingData<FilmData>>
}