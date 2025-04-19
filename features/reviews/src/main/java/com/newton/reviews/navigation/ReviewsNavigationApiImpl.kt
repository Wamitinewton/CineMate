package com.newton.reviews.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.newton.core.enums.TransitionType
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubgraphRoutes
import com.newton.navigation.NavigationTransitions
import com.newton.reviews.presentation.view.ReviewsScreen
import com.newton.reviews.presentation.viewModel.ReviewsViewModel

class ReviewsNavigationApiImpl: ReviewsNavigationApi {
    private val navigationTransitions = NavigationTransitions()

    override fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubgraphRoutes.Reviews.route,
            startDestination = NavigationRoutes.FilmReviews.routes,
        ) {
            composable(
                enterTransition = navigationTransitions.getEnterTransition(TransitionType.SLIDE_HORIZONTAL, 300),
                exitTransition = navigationTransitions.getExitTransition(TransitionType.SLIDE_HORIZONTAL, 300),
                popEnterTransition = navigationTransitions.getPopEnterTransition(TransitionType.SLIDE_HORIZONTAL, 300),
                popExitTransition = navigationTransitions.getPopExitTransition(TransitionType.SLIDE_HORIZONTAL, 300),
                route = NavigationRoutes.FilmReviews.routes,
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("isMovie") { type = NavType.BoolType }
                )
            ){ backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                val isMovie = backStackEntry.arguments?.getBoolean("isMovie") ?: true
                val viewModel = hiltViewModel<ReviewsViewModel>()
                ReviewsScreen(
                    filmId = id,
                    isMovie = isMovie,
                    onBackPressed = {
                        navHostController.popBackStack()
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}