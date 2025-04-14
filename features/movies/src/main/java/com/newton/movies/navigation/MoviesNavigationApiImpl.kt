package com.newton.movies.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.newton.movies.presentation.view.MoviesScreen
import com.newton.movies.presentation.view.movieDetails.MovieDetailsScreen
import com.newton.movies.presentation.viewModel.MovieDetailsViewModel
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

            composable(
                route = NavigationRoutes.MovieDetails.routes,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("id") ?: 0
                val viewModel = hiltViewModel<MovieDetailsViewModel>()

                MovieDetailsScreen(
                    viewModel = viewModel,
                    movieId = movieId,
                    onBackClick = {
                        navHostController.popBackStack()
                    }
                )
            }
        }
    }
}