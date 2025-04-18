package com.newton.shows.presentation.view.showsList

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.shared_ui.sharedComponents.*
import com.newton.shared_ui.theme.*
import com.newton.shows.presentation.viewModel.ShowsListViewModel

@Composable
fun ShowsScreen(
    viewModel: ShowsListViewModel,
    onMovieDetailsClick: (Int) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundGradient)
        ) {

            SearchCard(
                onSearchClick = { /* Your search click handler */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp, start = 30.dp, end = 30.dp)
                    .zIndex(1f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                OnTheAirShowsSection(
                    modifier = Modifier.fillMaxWidth(),
                    trendingMoviesFlow = viewModel.onTheAirShowList,
                    onRetry = {},
                    onMovieClick = { id ->
                        onMovieDetailsClick(id!!)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                TopRatedShowsSection(
                    modifier = Modifier.fillMaxWidth(),
                    trendingMoviesFlow = viewModel.topRatedShowList,
                    onRetry = {},
                    onMovieClick = { id ->
                        onMovieDetailsClick(id!!)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                AiringTodayShowSection(
                    modifier = Modifier.fillMaxWidth(),
                    trendingMoviesFlow = viewModel.airingTodayShowsList,
                    onRetry = {},
                    onMovieClick = { id ->
                        onMovieDetailsClick(id!!)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                PopularShowsSection(
                    modifier = Modifier.fillMaxWidth(),
                    trendingMoviesFlow = viewModel.popularShowsList,
                    onRetry = {},
                    onMovieClick = { id ->
                        onMovieDetailsClick(id!!)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}