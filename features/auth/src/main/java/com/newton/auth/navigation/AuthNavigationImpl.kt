package com.newton.auth.navigation

import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.auth.presentation.view.*
import com.newton.auth.presentation.viewModel.*
import com.newton.core.enums.TransitionType
import com.newton.navigation.*
import com.newton.prefs.*
import javax.inject.*

class AuthNavigationImpl @Inject constructor(
    private val prefsRepository: PrefsRepository
) : AuthNavigationApi {
    private val navigationTransitions = NavigationTransitions()

    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.Auth.route,
            startDestination = getStartDestination()
        ) {
            composable(
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.FADE, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.FADE, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.FADE, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.FADE, 300),
                route = NavigationRoutes.AuthScreenRoute.routes) {
                val authViewModel = hiltViewModel<TmdbAuthViewModel>()
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

            composable(
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.ZOOM, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.ZOOM, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.ZOOM, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.ZOOM, 300),
                route = NavigationRoutes.WelcomeScreenRoute.routes) {
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
