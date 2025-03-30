package com.newton.navigation

sealed class NavigationRoutes(val routes: String) {
    data object AuthScreenRoute: NavigationRoutes("auth")
    data object WelcomeScreenRoute: NavigationRoutes("welcome")
    data object HomeScreenRoute: NavigationRoutes("home")
}