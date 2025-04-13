package com.newton.trending.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.newton.shared_ui.components.SearchCard
import com.newton.shared_ui.theme.backgroundGradient
import com.newton.trending.presentation.viewModel.TrendingViewModel

@Composable
fun TrendingScreen(
    viewModel: TrendingViewModel
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
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                TrendingMoviesSection(
                    trendingMoviesFlow = viewModel.trendingMovies,
                    modifier = Modifier.fillMaxWidth(),
                    onRetry = {
                        viewModel.refresh()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                TrendingPeopleSection(
                    trendingPeopleFlow = viewModel.trendingPeople,
                    modifier = Modifier.fillMaxWidth(),
                    onRetry = {
                        viewModel.refresh()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }}

    }
