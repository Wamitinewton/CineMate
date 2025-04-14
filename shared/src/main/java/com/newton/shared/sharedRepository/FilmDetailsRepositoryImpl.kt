package com.newton.shared.sharedRepository

import com.newton.core.utils.*
import com.newton.network.*
import com.newton.network.data.mappers.*
import com.newton.network.data.remote.*
import com.newton.network.domain.models.*
import com.newton.network.domain.repositories.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class FilmDetailsRepositoryImpl @Inject constructor(
    private val moviesApiService: MoviesApiService
):FilmDetailsRepository  {

    override suspend fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>> =
        safeApiCall(
            apiCall = {
                val movieDetails =  moviesApiService.getMovieDetails(id).toDomainMovieDetails()
                movieDetails
            }
        )

}