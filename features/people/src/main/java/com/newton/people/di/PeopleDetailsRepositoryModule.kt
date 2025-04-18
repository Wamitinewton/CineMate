package com.newton.people.di

import com.newton.domain.repository.PeopleRepository
import com.newton.network.data.remote.FilmApiService
import com.newton.people.data.repository.PeopleDetailsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PeopleDetailsRepositoryModule {
    @Provides
    @Singleton
    fun provideDetailsRepository(
        filmApiService: FilmApiService
    ): PeopleRepository = PeopleDetailsRepositoryImpl(
        filmApiService = filmApiService
    )
}