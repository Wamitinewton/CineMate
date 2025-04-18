package com.newton.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.search.presentation.view.SearchScreen

class SearchNavigationApiImpl: SearchNavigationApi {
    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.Search.route,
            startDestination = NavigationRoutes.Search.routes
        ) {
            composable(
                route = NavigationRoutes.Search.routes
            ) {
                SearchScreen()
            }
        }
    }
}