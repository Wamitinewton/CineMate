package com.newton.movies.navigation

import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.movies.presentation.view.movieDetails.*
import com.newton.movies.presentation.view.movieLists.MoviesScreen
import com.newton.movies.presentation.viewModel.*
import com.newton.navigation.*

class MoviesNavigationApiImpl : MoviesNavigationApi {
    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.Movies.route,
            startDestination = NavigationRoutes.MoviesRoute.routes
        ) {
            composable(route = NavigationRoutes.MoviesRoute.routes) {
                val movieListViewModel = hiltViewModel<MovieListViewModel>()
                MoviesScreen(
                    viewModel = movieListViewModel,
                    onMovieDetailsClick = { id ->
                        navHostController.navigate(NavigationRoutes.MovieDetails.createRoute(id))
                    }
                )
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