package com.newton.network.data.remote

import com.newton.network.data.dto.ResponseDto
import com.newton.network.data.dto.TvShowResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingApiService {

    @GET(CineMateApiEndpoints.Trending.TV_TRENDING)
    suspend fun getTrendingShows(
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en"
    ): ResponseDto<List<TvShowResponseData>>
}