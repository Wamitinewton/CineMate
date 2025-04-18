package com.newton.trending.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.shared_ui.sharedComponents.*
import com.newton.shared_ui.theme.*
import com.newton.trending.presentation.viewModel.*

@Composable
fun TrendingScreen(
    viewModel: TrendingViewModel,
    onMovieDetailsClick: (Int) -> Unit,
    onShowsDetailsClick: (Int) -> Unit,
    onPeopleDetailsClick: (Int) -> Unit
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

                AllTrendingSection(
                    trendingShowsFlow = viewModel.allTrendingFilms,
                    modifier = Modifier.fillMaxWidth(),
                    onRetry = {
                        viewModel.refresh()
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                TrendingTvShowsSection(
                    trendingTvShowsFlow = viewModel.trendingShows,
                    modifier = Modifier.fillMaxWidth(),
                    onRetry = {
                        viewModel.refresh()
                    },
                    onShowsClick = { id ->
                        onShowsDetailsClick(id!!)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                TrendingMoviesSection(
                    trendingMoviesFlow = viewModel.trendingMovies,
                    modifier = Modifier.fillMaxWidth(),
                    onRetry = {
                        viewModel.refresh()
                    },
                    onMovieClick = { id ->
                        onMovieDetailsClick(id!!)
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))

                TrendingPeopleSection(
                    trendingPeopleFlow = viewModel.trendingPeople,
                    modifier = Modifier.fillMaxWidth(),
                    onRetry = {
                        viewModel.refresh()
                    },
                    onPeopleDetailsClick = { id ->
                        onPeopleDetailsClick(id!!)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}
