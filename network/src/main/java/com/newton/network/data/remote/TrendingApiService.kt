package com.newton.network.data.remote

import com.newton.network.data.dto.ResponseDto
import com.newton.network.data.dto.FilmResponseDto
import com.newton.network.data.dto.PeopleResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingApiService {

    @GET(CineMateApiEndpoints.Trending.TV_TRENDING)
    suspend fun getTrendingShows(
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en",
        @Query("include_adult") includeAdult: Boolean = true,
    ): ResponseDto<List<FilmResponseDto>>

    @GET(CineMateApiEndpoints.Trending.PEOPLE_TRENDING)
    suspend fun getTrendingPeople(
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en"
    ): ResponseDto<List<PeopleResponseDto>>

    @GET(CineMateApiEndpoints.Trending.ALL_TRENDING)
    suspend fun getAllTrending(
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en",
        @Query("include_adult") includeAdult: Boolean = true,
        ): ResponseDto<List<FilmResponseDto>>

    @GET(CineMateApiEndpoints.Trending.MOVIES_TRENDING)
    suspend fun getTrendingMovies(
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en",
        @Query("include_adult") includeAdult: Boolean = true,
        ): ResponseDto<List<FilmResponseDto>>
}