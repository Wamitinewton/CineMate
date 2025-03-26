package com.newton.cinemate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.newton.navigation.NavigationSubgraphRoutes

@Composable
fun CineMateNavigation(
    navigationSubGraphs: NavigationSubGraphs,
    navHostController: NavHostController
) {

    NavHost(
        navController = navHostController,
        startDestination = NavigationSubgraphRoutes.Auth.route
    ){
        navigationSubGraphs.authNavigationApi.registerNavigationGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
    }
}