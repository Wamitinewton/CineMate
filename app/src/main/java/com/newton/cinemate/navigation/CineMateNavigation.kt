package com.newton.cinemate.navigation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.cinemate.viewModel.*

@Composable
fun CineMateNavigation(
    navigationSubGraphs: NavigationSubGraphs,
    navHostController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val startDestination by viewModel.startDestination.collectAsState()

    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        navigationSubGraphs.authNavigationApi.registerNavigationGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.trendingNavigationApi.registerNavigationGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.moviesNavigationApi.registerNavigationGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.showsNavigationApi.registerNavigationGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.searchNavigationApi.registerNavigationGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.peopleNavigationApi.registerNavigationGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.reviewsNavigationApi.registerNavigationGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
    }
}