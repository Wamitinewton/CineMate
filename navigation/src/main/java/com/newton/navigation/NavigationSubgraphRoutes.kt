package com.newton.navigation

sealed class NavigationSubgraphRoutes(val route: String) {
    data object Auth: NavigationSubgraphRoutes(route = "/auth_")
    data object Trending: NavigationSubgraphRoutes(route = "/trending_")
}