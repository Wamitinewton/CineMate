package com.newton.navigation

sealed class NavigationSubgraphRoutes(val route: String) {
    data object Auth: NavigationSubgraphRoutes(route = "/auth_")
}