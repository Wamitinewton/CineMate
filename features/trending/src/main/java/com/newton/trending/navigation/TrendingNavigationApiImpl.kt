package com.newton.trending.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.trending.presentation.view.TrendingScreen
import com.newton.trending.presentation.viewModel.TrendingViewModel

class TrendingNavigationApiImpl: TrendingNavigationApi {
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
                    viewModel = trendingViewModel
                )
            }
        }
    }
}