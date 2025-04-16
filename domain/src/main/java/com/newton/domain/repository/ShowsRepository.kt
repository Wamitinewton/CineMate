package com.newton.domain.repository

import com.newton.core.utils.Resource
import com.newton.domain.models.FilmDetails
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {
    suspend fun getShowsDetails(id: Int): Flow<Resource<FilmDetails>>
}