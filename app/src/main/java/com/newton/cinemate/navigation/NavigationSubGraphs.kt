package com.newton.cinemate.navigation

import com.newton.auth.navigation.AuthNavigationApi
import com.newton.trending.navigation.TrendingNavigationApi

data class NavigationSubGraphs(
    val authNavigationApi: AuthNavigationApi,
    val trendingNavigationApi: TrendingNavigationApi
)
