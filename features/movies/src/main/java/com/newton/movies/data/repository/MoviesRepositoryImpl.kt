package com.newton.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.newton.core.enums.MovieCategory
import com.newton.core.utils.Resource
import com.newton.domain.models.FilmData
import com.newton.network.data.mappers.toFilmDomainDetails
import com.newton.network.data.remote.FilmApiService
import com.newton.domain.models.FilmDetails
import com.newton.domain.repository.MoviesRepository
import com.newton.network.data.mappers.toAllTrendingDomainList
import com.newton.network.paging.GenericPagingSource
import com.newton.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApiService: FilmApiService
) : MoviesRepository {

    override suspend fun getMovieDetails(id: Int): Flow<Resource<FilmDetails>> =
        safeApiCall(
            apiCall = {
                val movieDetails = moviesApiService.getMovieDetails(id).toFilmDomainDetails()
                movieDetails
            }
        )

    override  fun getListOfMovies(movieCategory: MovieCategory): Flow<PagingData<FilmData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                GenericPagingSource(
                    apiCall = { page ->
                        moviesApiService.getListOfMovies(
                            page = page,
                            url = movieCategory.endpoint
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