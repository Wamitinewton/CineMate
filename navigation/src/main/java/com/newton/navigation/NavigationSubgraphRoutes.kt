package com.newton.navigation

sealed class NavigationSubgraphRoutes(val route: String) {
    data object Auth : NavigationSubgraphRoutes(route = "/auth_")
    data object Movies : NavigationSubgraphRoutes(route = "/movies_")
    data object Shows : NavigationSubgraphRoutes(route = "/shows_")
    data object Search : NavigationSubgraphRoutes(route = "/search_")
    data object Settings : NavigationSubgraphRoutes(route = "/settings_")
    data object Trending : NavigationSubgraphRoutes(route = "/trending_")
    data object People : NavigationSubgraphRoutes(route = "/people_")
    data object Reviews: NavigationSubgraphRoutes(route = "/reviews_")
}