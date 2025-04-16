package com.newton.auth.navigation

import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.auth.presentation.view.*
import com.newton.auth.presentation.viewModel.*
import com.newton.navigation.*
import com.newton.prefs.*
import javax.inject.*

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
            prefsRepository.isGuestUser()
        ) {
            NavigationRoutes.WelcomeScreenRoute.routes
        } else {
            NavigationRoutes.AuthScreenRoute.routes
        }
    }
}
