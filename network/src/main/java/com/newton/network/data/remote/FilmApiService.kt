package com.newton.network.data.remote

import com.newton.network.data.dto.*
import retrofit2.http.*

interface FilmApiService {

    @GET(CineMateApiEndpoints.Trending.TV_TRENDING)
    suspend fun getTrendingShows(
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en",
        @Query("include_adult") includeAdult: Boolean = true,
    ): PagingResponseDto<List<FilmResponseDto>>

    @GET(CineMateApiEndpoints.Trending.PEOPLE_TRENDING)
    suspend fun getTrendingPeople(
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en"
    ): PagingResponseDto<List<PeopleResponseDto>>

    @GET(CineMateApiEndpoints.Trending.ALL_TRENDING)
    suspend fun getAllTrending(
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en",
        @Query("include_adult") includeAdult: Boolean = true,
    ): PagingResponseDto<List<FilmResponseDto>>

    @GET(CineMateApiEndpoints.Trending.MOVIES_TRENDING)
    suspend fun getTrendingMovies(
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en",
        @Query("include_adult") includeAdult: Boolean = true,
    ): PagingResponseDto<List<FilmResponseDto>>

    @GET(CineMateApiEndpoints.Shows.SERIES_DETAILS)
    suspend fun getSeriesDetails(
        @Path("series_id") id: Int
    ): FilmDetailsDto

    @GET(CineMateApiEndpoints.Movies.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int
    ): FilmDetailsDto

}