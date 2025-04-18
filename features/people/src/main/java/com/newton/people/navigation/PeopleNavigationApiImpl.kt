package com.newton.people.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.people.presentation.view.PeopleDetailsScreen
import com.newton.people.presentation.viewModel.PeopleDetailsViewModel

class PeopleNavigationApiImpl: PeopleNavigationApi {
    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.People.route,
            startDestination = NavigationRoutes.PeopleDetails.routes,
        ) {
            composable(
                route = NavigationRoutes.PeopleDetails.routes,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val personId = backStackEntry.arguments?.getInt("id") ?: 0
                val viewModel = hiltViewModel<PeopleDetailsViewModel>()

                PeopleDetailsScreen(
                    personId = personId,
                    onNavigateBack = {
                        navHostController.popBackStack()
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}