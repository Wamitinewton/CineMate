package com.newton.domain.repository

import androidx.paging.PagingData
import com.newton.core.enums.ShowCategory
import com.newton.core.utils.Resource
import com.newton.domain.models.FilmData
import com.newton.domain.models.FilmDetails
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {
    suspend fun getShowsDetails(id: Int): Flow<Resource<FilmDetails>>

     fun getListOfShows(showCategory: ShowCategory ,id: Int? = null): Flow<PagingData<FilmData>>
}