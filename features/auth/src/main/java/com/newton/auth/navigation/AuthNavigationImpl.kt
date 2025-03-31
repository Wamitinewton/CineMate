package com.newton.auth.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.auth.presentation.view.OnboardingScreen
import com.newton.auth.presentation.view.WelcomeScreen
import com.newton.auth.presentation.viewModel.AuthViewModel
import com.newton.auth.presentation.viewModel.WelcomeViewModel
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.prefs.PrefsRepository
import javax.inject.Inject

class AuthNavigationImpl @Inject constructor(
    private val prefsRepository: PrefsRepository
) : AuthNavigationApi {

    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.Auth.route,
            startDestination = getStartDestination()
        ) {
            composable(route = NavigationRoutes.AuthScreenRoute.routes) {
                val authViewModel = hiltViewModel<AuthViewModel>()
                OnboardingScreen(
                    onAuthSuccess = {
                        prefsRepository.setUserOnboardingStatus(true)
                        navHostController.navigate(NavigationRoutes.WelcomeScreenRoute.routes) {
                            popUpTo(NavigationRoutes.AuthScreenRoute.routes) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    viewModel = authViewModel,
                    onContinueWithoutAccountClick = {
                        prefsRepository.setUserOnboardingStatus(true)
                        prefsRepository.setGuestUser(true)
                        navHostController.navigate(NavigationRoutes.WelcomeScreenRoute.routes) {
                            popUpTo(NavigationRoutes.AuthScreenRoute.routes) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(route = NavigationRoutes.WelcomeScreenRoute.routes) {
                val welcomeViewModel = hiltViewModel<WelcomeViewModel>()
                WelcomeScreen(
                    navigateToHome = {
                        prefsRepository.setUserOnboardingStatus(true)
                        navHostController.navigate(NavigationRoutes.HomeScreenRoute.routes) {
                            popUpTo(NavigationSubgraphRoutes.Auth.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    viewModel = welcomeViewModel
                )
            }
        }
    }

    private fun getStartDestination(): String {
        return if (prefsRepository.getUserOnboardingStatus() &&
            prefsRepository.isGuestUser()) {
            NavigationRoutes.WelcomeScreenRoute.routes
        } else {
            NavigationRoutes.AuthScreenRoute.routes
        }
    }
}
