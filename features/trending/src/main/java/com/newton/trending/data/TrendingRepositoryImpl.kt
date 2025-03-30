package com.newton.trending.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.newton.network.data.mappers.toAllTrendingDomainList
import com.newton.network.data.remote.TrendingApiService
import com.newton.network.domain.models.TvShowData
import com.newton.network.domain.repositories.TrendingRepository
import com.newton.network.paging.GenericPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrendingRepositoryImpl @Inject constructor(
    private val trendingApiService: TrendingApiService
): TrendingRepository {
    override fun getTrendingShows(): Flow<PagingData<TvShowData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingSource(
                    apiCall = { page ->
                        trendingApiService.getTrendingShows(page = page)
                    },
                    dataMapper = { response ->
                        response.results.toAllTrendingDomainList()
                    },
                    getNextPageNumber = { response ->
                        if (response.results.isEmpty()) null else response.page + 1
                    }
                )
            }
        ).flow
    }
}