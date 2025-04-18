package com.newton.people.di

import com.newton.people.navigation.PeopleNavigationApi
import com.newton.people.navigation.PeopleNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PeopleNavigationModule {

    @Provides
    @Singleton
    fun providePeopleNavigationModule(): PeopleNavigationApi = PeopleNavigationApiImpl()
}