package com.newton.cinemate.activity

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.newton.cinemate.navigation.BottomNavigationBar
import com.newton.cinemate.navigation.CineMateNavigation
import com.newton.cinemate.navigation.NavigationSubGraphs
import com.newton.navigation.NavigationRoutes
import com.newton.shared_ui.theme.CineMateTheme

@Composable
fun RootScreen(navigationSubGraphs: NavigationSubGraphs) {
    val navController = rememberNavController()
    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntryAsState?.destination
    val context = LocalContext.current

    val isShowBottomBar = when (currentDestination?.route) {
        NavigationRoutes.HomeScreenRoute.routes, NavigationRoutes.MoviesRoute.routes -> true
        else -> false
    }

    if (currentDestination?.route == NavigationRoutes.HomeScreenRoute.routes) {
        BackHandler {
            (context as? Activity)?.finish()
        }
    }

    CineMateTheme {
        Scaffold(
            bottomBar = {
                if (isShowBottomBar) {
                    BottomNavigationBar(
                        navController = navController,
                        currentDestination = currentDestination
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                CineMateNavigation(
                    navigationSubGraphs = navigationSubGraphs,
                    navHostController = navController
                )
            }
        }
    }
}