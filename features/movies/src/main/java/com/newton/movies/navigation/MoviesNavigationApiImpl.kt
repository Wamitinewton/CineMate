package com.newton.movies.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.movies.presentation.view.MoviesScreen
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes

class MoviesNavigationApiImpl: MoviesNavigationApi {
    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.Movies.route,
            startDestination = NavigationRoutes.MoviesRoute.routes
        ){
            composable(route = NavigationRoutes.MoviesRoute.routes) {
                MoviesScreen()
            }
        }
    }
}