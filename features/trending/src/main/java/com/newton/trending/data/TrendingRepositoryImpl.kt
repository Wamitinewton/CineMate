package com.newton.trending.data

import androidx.paging.*
import com.newton.core.enums.MovieCategory
import com.newton.core.enums.ShowCategory
import com.newton.domain.models.FilmData
import com.newton.domain.models.PeopleData
import com.newton.domain.repository.TrendingRepository
import com.newton.network.data.mappers.*
import com.newton.network.data.remote.*
import com.newton.network.paging.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class TrendingRepositoryImpl @Inject constructor(
    private val filmApiService: FilmApiService
) : TrendingRepository {
    override fun getTrendingShows(allowAdult: Boolean, showCategory: ShowCategory): Flow<PagingData<FilmData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingSource(
                    apiCall = { page ->
                        filmApiService.getListOfShows(page = page, includeAdult = allowAdult, url = showCategory.endpoint)
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

    override fun getTrendingPeople(): Flow<PagingData<PeopleData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                GenericPagingSource(
                    apiCall = { page ->
                        filmApiService.getTrendingPeople(page = page)
                    },
                    dataMapper = { response ->
                        response.results.toDomainPeopleList()
                    },
                    getNextPageNumber = { response ->
                        if (response.results.isEmpty()) null else response.page + 1
                    }
                )
            }
        ).flow
    }

    override fun getAllTrendingFilms(): Flow<PagingData<FilmData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                GenericPagingSource(
                    apiCall = { page ->
                        filmApiService.getAllTrending(page = page)
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

    override fun getTrendingMovies(category: MovieCategory): Flow<PagingData<FilmData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                GenericPagingSource(
                    apiCall = { page ->
                        filmApiService.getListOfMovies(
                            page = page,
                            url = category.endpoint
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