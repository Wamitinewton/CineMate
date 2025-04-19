package com.newton.trending.navigation

import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.core.enums.*
import com.newton.navigation.*
import com.newton.trending.presentation.view.*
import com.newton.trending.presentation.viewModel.*

class TrendingNavigationApiImpl : TrendingNavigationApi {
    private val navigationTransitions = NavigationTransitions()

    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.Trending.route,
            startDestination = NavigationRoutes.HomeScreenRoute.routes
        ) {
            composable(
                enterTransition = navigationTransitions.getEnterTransition(
                    TransitionType.ZOOM,
                    300
                ),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(
                    TransitionType.ZOOM,
                    300
                ),
                popExitTransition = navigationTransitions.getPopExitTransition(
                    TransitionType.ZOOM,
                    300
                ),
                route = NavigationRoutes.HomeScreenRoute.routes
            ) {
                val trendingViewModel = hiltViewModel<TrendingViewModel>()
                TrendingScreen(
                    viewModel = trendingViewModel,
                    onMovieDetailsClick = { id ->
                        navHostController.navigate(NavigationRoutes.MovieDetails.createRoute(id))
                    },
                    onShowsDetailsClick = { id ->
                        navHostController.navigate(NavigationRoutes.ShowsDetails.createRoute(id))
                    },
                    onPeopleDetailsClick = { id ->
                        navHostController.navigate(NavigationRoutes.PeopleDetails.createRoute(id))
                    }
                )
            }
        }
    }
}