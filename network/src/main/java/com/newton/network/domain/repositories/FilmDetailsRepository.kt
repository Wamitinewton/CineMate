package com.newton.network.domain.repositories

import com.newton.core.utils.*
import com.newton.network.domain.models.*
import kotlinx.coroutines.flow.*

interface FilmDetailsRepository {
    suspend fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>>
}