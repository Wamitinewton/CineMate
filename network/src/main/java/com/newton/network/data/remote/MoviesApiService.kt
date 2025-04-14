package com.newton.network.data.remote

import com.newton.network.data.dto.*
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApiService {

    @GET(CineMateApiEndpoints.Movies.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int
    ): MovieResponseDto
}