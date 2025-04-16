package com.newton.movies.data.repository

import com.newton.core.utils.Resource
import com.newton.network.data.mappers.toFilmDomainDetails
import com.newton.network.data.remote.FilmApiService
import com.newton.domain.models.FilmDetails
import com.newton.domain.repository.MoviesRepository
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

}