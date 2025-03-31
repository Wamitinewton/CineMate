package com.newton.cinemate.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.ui.graphics.vector.ImageVector
import com.newton.navigation.NavigationSubgraphRoutes

sealed class Screens(
    var route: String,
    var selectedIcon: ImageVector,
    var unSelectedIcon: ImageVector,
    var title: String
) {
    data object Home: Screens(
        NavigationSubgraphRoutes.Trending.route,
        Icons.Filled.Home,
        Icons.Outlined.Home,
        "Trending"
    )

    data object Movies: Screens(
        NavigationSubgraphRoutes.Movies.route,
        Icons.Filled.Movie,
        Icons.Outlined.Movie,
        "Trending"
    )
}

var bottomNavigationDestinations = listOf(
    Screens.Home,
    Screens.Movies
)