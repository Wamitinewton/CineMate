package com.newton.shows.navigation

import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.navigation.*
import com.newton.shows.presentation.view.showDetails.*
import com.newton.shows.presentation.view.showsList.*
import com.newton.shows.presentation.viewModel.*

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
                    }
                )
            }

        }
    }
}