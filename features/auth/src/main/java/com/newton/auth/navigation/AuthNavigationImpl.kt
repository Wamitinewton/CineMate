package com.newton.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.auth.presentation.view.LoginScreen
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes

class AuthNavigationImpl: AuthNavigationApi {
    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.Auth.route,
            startDestination = NavigationRoutes.AuthScreen.routes
        ){
            composable(route = NavigationRoutes.AuthScreen.routes) {
                LoginScreen(
                    onLoginClick = {},
                )
            }
        }
    }
}