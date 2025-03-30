package com.newton.network.domain.repositories

import androidx.paging.PagingData
import com.newton.network.domain.models.TvShowData
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {
    fun getTrendingShows() : Flow<PagingData<TvShowData>>
}