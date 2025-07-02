package com.newton.network.data.remote

import com.newton.network.data.dto.*
import retrofit2.http.*

interface TmdbAuthApiService {

    @GET("authentication/token/new")
    suspend fun createRequestToken(): RequestTokenDto

    @POST("authentication/session/new")
    suspend fun createSession(
        @Query("request_token") requestToken: String
    ): SessionDto

    @GET("account")
    suspend fun getAccountDetails(
        @Query("session_id") sessionId: String
    ): AccountDetailsDto

    @DELETE("authentication/session")
    suspend fun deleteSession(
        @Body deleteSessionRequest: DeleteSessionRequest
    ): AuthErrorDto
}