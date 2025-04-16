package com.newton.trending.navigation

import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.navigation.*
import com.newton.trending.presentation.view.*
import com.newton.trending.presentation.viewModel.*

class TrendingNavigationApiImpl : TrendingNavigationApi {
    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.Trending.route,
            startDestination = NavigationRoutes.HomeScreenRoute.routes
        ) {
            composable(route = NavigationRoutes.HomeScreenRoute.routes) {
                val trendingViewModel = hiltViewModel<TrendingViewModel>()
                TrendingScreen(
                    viewModel = trendingViewModel,
                    onMovieDetailsClick = { id ->
                        navHostController.navigate(NavigationRoutes.MovieDetails.createRoute(id))
                    },
                    onShowsDetailsClick = { id ->
                        navHostController.navigate(NavigationRoutes.ShowsDetails.createRoute(id))
                    }
                )
            }
        }
    }
}