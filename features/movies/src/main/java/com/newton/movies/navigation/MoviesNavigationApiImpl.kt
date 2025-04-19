package com.newton.movies.navigation

import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.core.enums.TransitionType
import com.newton.movies.presentation.view.movieDetails.*
import com.newton.movies.presentation.view.movieLists.MoviesScreen
import com.newton.movies.presentation.viewModel.*
import com.newton.navigation.*

class MoviesNavigationApiImpl : MoviesNavigationApi {

    private val navigationTransitions = NavigationTransitions()

    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.Movies.route,
            startDestination = NavigationRoutes.MoviesRoute.routes
        ) {
            composable(
                route = NavigationRoutes.MoviesRoute.routes,
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300)
            ) {
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
                ),
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.SLIDE_HORIZONTAL, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.SLIDE_HORIZONTAL, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.SLIDE_HORIZONTAL, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.SLIDE_HORIZONTAL, 300)
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("id") ?: 0
                val viewModel = hiltViewModel<MovieDetailsViewModel>()
                val movieListViewModel = hiltViewModel<MovieListViewModel>()

                MovieDetailsScreen(
                    movieDetailsViewModel = viewModel,
                    movieId = movieId,
                    onBackClick = {
                        navHostController.popBackStack()
                    },
                    movieListViewModel = movieListViewModel,
                    onSimilarMovieClick = { id ->
                        navHostController.navigate(NavigationRoutes.MovieDetails.createRoute(id))
                    },
                    onReviewsClicked = { id, isMovie ->
                        navHostController.navigate(NavigationRoutes.FilmReviews.createRoute(id, isMovie))
                    }
                )
            }
        }
    }
}