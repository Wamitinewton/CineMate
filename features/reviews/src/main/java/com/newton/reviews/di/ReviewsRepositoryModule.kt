package com.newton.reviews.di

import com.newton.domain.repository.ReviewsRepository
import com.newton.network.data.remote.FilmApiService
import com.newton.reviews.data.ReviewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReviewsRepositoryModule {

    @Provides
    @Singleton
    fun provideReviewsRepository(
        filmApiService: FilmApiService
    ): ReviewsRepository = ReviewsRepositoryImpl(filmApiService)
}