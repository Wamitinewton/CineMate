package com.newton.cinemate.navigation

import com.newton.auth.navigation.*
import com.newton.movies.navigation.*
import com.newton.people.navigation.PeopleNavigationApi
import com.newton.search.navigation.SearchNavigationApi
import com.newton.shows.navigation.ShowsNavigationApi
import com.newton.trending.navigation.*

data class NavigationSubGraphs(
    val authNavigationApi: AuthNavigationApi,
    val trendingNavigationApi: TrendingNavigationApi,
    val moviesNavigationApi: MoviesNavigationApi,
    val showsNavigationApi: ShowsNavigationApi,
    val searchNavigationApi: SearchNavigationApi,
    val peopleNavigationApi: PeopleNavigationApi
)
