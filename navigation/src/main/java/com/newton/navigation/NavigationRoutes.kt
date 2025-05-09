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

    data object PeopleDetails : NavigationRoutes("peopleDetails/{id}") {
        fun createRoute(id: Int) = "peopleDetails/$id"
    }

    data object FilmReviews : NavigationRoutes("filmReviews/{id}/{isMovie}") {
        fun createRoute(id: Int, isMovie: Boolean) = "filmReviews/$id/$isMovie"
    }
}