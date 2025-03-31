package com.newton.navigation

sealed class NavigationSubgraphRoutes(val route: String) {
    data object Auth: NavigationSubgraphRoutes(route = "/auth_")
    data object Movies: NavigationSubgraphRoutes(route = "/movies")
    data object Shows: NavigationSubgraphRoutes(route = "/shows")
    data object Search: NavigationSubgraphRoutes(route = "/search")
    data object Settings: NavigationSubgraphRoutes(route = "/settings")
    data object Trending: NavigationSubgraphRoutes(route = "/trending_")
}