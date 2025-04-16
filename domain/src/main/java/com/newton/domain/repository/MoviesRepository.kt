package com.newton.domain.repository

import com.newton.core.utils.*
import com.newton.domain.models.FilmDetails
import kotlinx.coroutines.flow.*

interface MoviesRepository {
    suspend fun getMovieDetails(id: Int): Flow<Resource<FilmDetails>>
}