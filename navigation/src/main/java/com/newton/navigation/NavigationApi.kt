package com.newton.navigation

import androidx.navigation.*

interface NavigationApi {
    fun registerNavigationGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    )
}