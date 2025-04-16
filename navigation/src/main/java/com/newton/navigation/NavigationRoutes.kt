package com.newton.navigation

sealed class NavigationRoutes(val routes: String) {
    data object AuthScreenRoute : NavigationRoutes("auth")
    data object WelcomeScreenRoute : NavigationRoutes("welcome")
    data object HomeScreenRoute : NavigationRoutes("home")
    data object MoviesRoute : NavigationRoutes("movies")
    data object Search : NavigationRoutes("search")
    data object Settings : NavigationRoutes("settings")
    data object Shows : NavigationRoutes("shows")
    data object MovieDetails : NavigationRoutes("movieDetails/{id}") {
        fun createRoute(id: Int) = "movieDetails/$id"
    }
    data object ShowsDetails : NavigationRoutes("showDetails/{id}") {
        fun createRoute(id: Int) = "showDetails/$id"
    }
}