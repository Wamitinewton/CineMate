package com.newton.shows.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.shows.presentation.view.showDetails.ShowDetailsScreen
import com.newton.shows.presentation.view.showsList.ShowScreen
import com.newton.shows.presentation.viewModel.ShowsDetailsViewModel

class ShowsNavigationApiImpl: ShowsNavigationApi {
    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.Shows.route,
            startDestination = NavigationRoutes.Shows.routes
        ) {
            composable(
                route = NavigationRoutes.Shows.routes
            ) {
                ShowScreen()
            }

            composable(
                route = NavigationRoutes.ShowsDetails.routes,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val seriesId = backStackEntry.arguments?.getInt("id") ?: 0
                val viewModel = hiltViewModel<ShowsDetailsViewModel>()
                ShowDetailsScreen(
                    viewModel = viewModel,
                    seriesId = seriesId,
                    onBackClick = {
                        navHostController.popBackStack()
                    }
                )
            }

        }
    }
}