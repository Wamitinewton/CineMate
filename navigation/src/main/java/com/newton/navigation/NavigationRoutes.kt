package com.newton.navigation

sealed class NavigationRoutes(val routes: String) {
    data object AuthScreenRoute: NavigationRoutes("auth")
    data object WelcomeScreenRoute: NavigationRoutes("welcome")
    data object HomeScreenRoute: NavigationRoutes("home")
    data object MoviesRoute: NavigationRoutes("movies")
    data object Search: NavigationRoutes("search")
    data object Settings: NavigationRoutes("settings")
    data object Shows: NavigationRoutes("shows")
}