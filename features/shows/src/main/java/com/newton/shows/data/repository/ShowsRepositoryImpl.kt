package com.newton.shows.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.newton.core.enums.MovieCategory
import com.newton.core.enums.ShowCategory
import com.newton.core.utils.Resource
import com.newton.domain.models.FilmData
import com.newton.network.data.mappers.toFilmDomainDetails
import com.newton.network.data.remote.FilmApiService
import com.newton.domain.models.FilmDetails
import com.newton.domain.repository.ShowsRepository
import com.newton.network.data.mappers.toAllTrendingDomainList
import com.newton.network.paging.GenericPagingSource
import com.newton.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowsRepositoryImpl @Inject constructor(
    private val filmApiService: FilmApiService
): ShowsRepository {

    override suspend fun getShowsDetails(id: Int): Flow<Resource<FilmDetails>> =
        safeApiCall(
            apiCall = {
                val showDetails = filmApiService.getSeriesDetails(id).toFilmDomainDetails()
                showDetails
            }
        )

    override  fun getListOfShows(showCategory: ShowCategory ,id: Int?): Flow<PagingData<FilmData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                GenericPagingSource(
                    apiCall = { page ->

                        val url = if (showCategory == ShowCategory.SIMILAR && id != null) {
                            showCategory.endpoint.replace("{series_id}", id.toString())
                        } else {
                            showCategory.endpoint
                        }
                        filmApiService.getListOfMovies(
                            page = page,
                            url = url
                        )
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