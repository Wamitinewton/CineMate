package com.newton.cinemate.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.newton.cinemate.navigation.CineMateNavigation
import com.newton.cinemate.navigation.NavigationSubGraphs
import com.newton.shared_ui.theme.CineMateTheme

@Composable
fun RootScreen(navigationSubGraphs: NavigationSubGraphs) {
    val navController = rememberNavController()

    CineMateTheme {
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize().padding(it)
            ) {
                CineMateNavigation(
                    navigationSubGraphs = navigationSubGraphs,
                    navHostController = navController
                )
            }
        }
    }

}
