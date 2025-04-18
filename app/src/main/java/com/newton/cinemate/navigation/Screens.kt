package com.newton.cinemate.navigation

import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.*
import com.newton.navigation.*

sealed class Screens(
    var route: String,
    var selectedIcon: ImageVector,
    var unSelectedIcon: ImageVector,
    var title: String
) {
    data object Home : Screens(
        NavigationSubgraphRoutes.Trending.route,
        Icons.Filled.Home,
        Icons.Outlined.Home,
        "Trending"
    )

    data object Movies : Screens(
        NavigationSubgraphRoutes.Movies.route,
        Icons.Filled.Movie,
        Icons.Outlined.Movie,
        "Movies"
    )

    data object Shows: Screens(
        NavigationSubgraphRoutes.Shows.route,
        Icons.Filled.Tv,
        Icons.Outlined.Tv,
        "Shows"
    )
}

var bottomNavigationDestinations = listOf(
    Screens.Home,
    Screens.Movies,
    Screens.Shows
)