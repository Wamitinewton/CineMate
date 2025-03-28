package com.newton.cinemate.di

import com.newton.cinemate.BuildConfig
import com.newton.network.data.interceptor.RequestInterceptor
import com.newton.network.data.remote.CineMateApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private const val API_KEY = BuildConfig.TMDB_API
    private const val BASE_URL = "https://api.themoviedb.org/3"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor(API_KEY))
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideCineMateApi(okHttpClient: OkHttpClient): CineMateApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CineMateApiService::class.java)
    }
}