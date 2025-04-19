package com.newton.reviews.di

import com.newton.reviews.navigation.ReviewsNavigationApi
import com.newton.reviews.navigation.ReviewsNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FilmReviewNavigationModule {

    @Provides
    @Singleton
    fun provideFilmReviewModule(): ReviewsNavigationApi = ReviewsNavigationApiImpl()
}