package com.newton.cinemate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.newton.cinemate.viewModel.MainViewModel

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
    ){
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
    }
}