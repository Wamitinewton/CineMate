package com.newton.auth.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.auth.presentation.view.OnboardingScreen
import com.newton.auth.presentation.viewModel.AuthViewModel
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
                val authViewModel = hiltViewModel<AuthViewModel>()
                OnboardingScreen(
                    onAuthSuccess = {},
                    viewModel = authViewModel
                )
            }
        }
    }
}