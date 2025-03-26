package com.newton.navigation

sealed class NavigationRoutes(val routes: String) {
    data object AuthScreen: NavigationRoutes("auth")
}