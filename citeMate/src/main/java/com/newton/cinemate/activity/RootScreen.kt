package com.newton.cinemate.activity

import android.app.*
import androidx.activity.compose.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.navigation.compose.*
import com.newton.cinemate.navigation.*
import com.newton.navigation.*
import com.newton.shared_ui.sharedComponents.*
import com.newton.shared_ui.theme.*

@Composable
fun RootScreen(navigationSubGraphs: NavigationSubGraphs) {
    val navController = rememberNavController()
    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntryAsState?.destination
    val context = LocalContext.current

    val isShowBottomBar = when (currentDestination?.route) {
        NavigationRoutes.HomeScreenRoute.routes, NavigationRoutes.MoviesRoute.routes, NavigationRoutes.Shows.routes -> true
        else -> false
    }

    if (currentDestination?.route == NavigationRoutes.HomeScreenRoute.routes) {
        BackHandler {
            (context as? Activity)?.finish()
        }
    }

    var isInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isInitialized = true
    }

        CineMateTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AnimatedVisibility(
                    visible = isInitialized,
                    enter = fadeIn(animationSpec = tween(300))
                ) {
                    Scaffold(
                        bottomBar = {
                            if (isShowBottomBar) {
                                NotchedBottomNavigationBar(
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

                if (!isInitialized) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(backgroundGradient),
                    ) {
                        CustomLoadingIndicator(
                            text = "Loading..."
                        )
                    }
                }
            }
        }

    }
