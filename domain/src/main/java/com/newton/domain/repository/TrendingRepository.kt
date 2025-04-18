package com.newton.domain.repository

import androidx.paging.*
import com.newton.core.enums.MovieCategory
import com.newton.core.enums.ShowCategory
import com.newton.domain.models.FilmData
import com.newton.domain.models.PeopleData
import kotlinx.coroutines.flow.*

interface TrendingRepository {
    fun getTrendingShows(allowAdult: Boolean, showCategory: ShowCategory): Flow<PagingData<FilmData>>

    fun getTrendingPeople(): Flow<PagingData<PeopleData>>

    fun getAllTrendingFilms(): Flow<PagingData<FilmData>>

    fun getTrendingMovies(category: MovieCategory): Flow<PagingData<FilmData>>
}