package com.newton.shows.navigation

import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.core.enums.TransitionType
import com.newton.navigation.*
import com.newton.shows.presentation.view.showDetails.*
import com.newton.shows.presentation.view.showsList.*
import com.newton.shows.presentation.viewModel.*

class ShowsNavigationApiImpl: ShowsNavigationApi {
    private val navigationTransitions = NavigationTransitions()

    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.Shows.route,
            startDestination = NavigationRoutes.Shows.routes
        ) {
            composable(
                route = NavigationRoutes.Shows.routes,
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300)
            ) {
                val showsListViewModel = hiltViewModel<ShowsListViewModel>()
                ShowsScreen(
                    viewModel = showsListViewModel,
                    onMovieDetailsClick = { id ->
                        navHostController.navigate(NavigationRoutes.ShowsDetails.createRoute(id))
                    }
                )
            }

            composable(
                route = NavigationRoutes.ShowsDetails.routes,
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.SLIDE_HORIZONTAL, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.SLIDE_HORIZONTAL, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.SLIDE_HORIZONTAL, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.SLIDE_HORIZONTAL, 300),
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val seriesId = backStackEntry.arguments?.getInt("id") ?: 0
                val viewModel = hiltViewModel<ShowsDetailsViewModel>()
                val showsListViewModel = hiltViewModel<ShowsListViewModel>()
                ShowDetailsScreen(
                    viewModel = viewModel,
                    seriesId = seriesId,
                    onBackClick = {
                        navHostController.popBackStack()
                    },
                    showsListViewModel = showsListViewModel,
                    onSimilarShowClick = { id ->
                        navHostController.navigate(NavigationRoutes.ShowsDetails.createRoute(id))
                    },
                    onReviewClicked = { id, isMovie ->
                        navHostController.navigate(NavigationRoutes.FilmReviews.createRoute(id, isMovie))
                    }
                )
            }

        }
    }
}