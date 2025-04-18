package com.newton.movies.presentation.view.movieLists

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.movies.presentation.viewModel.*
import com.newton.shared_ui.sharedComponents.*
import com.newton.shared_ui.theme.*

@Composable
fun MoviesScreen(
    viewModel: MovieListViewModel,
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

                UpcomingMoviesSection(
                    modifier = Modifier.fillMaxWidth(),
                    trendingMoviesFlow = viewModel.upcomingMovieList,
                    onRetry = {},
                    onMovieClick = { id ->
                        onMovieDetailsClick(id!!)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                TopRatedMoviesSection(
                    modifier = Modifier.fillMaxWidth(),
                    trendingMoviesFlow = viewModel.topRatedMovieList,
                    onRetry = {},
                    onMovieClick = { id ->
                        onMovieDetailsClick(id!!)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                NowPlayingMoviesSection(
                    modifier = Modifier.fillMaxWidth(),
                    trendingMoviesFlow = viewModel.nowPlayingMovieList,
                    onRetry = {},
                    onMovieClick = { id ->
                        onMovieDetailsClick(id!!)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                PopularMoviesSection(
                    modifier = Modifier.fillMaxWidth(),
                    trendingMoviesFlow = viewModel.popularMovieList,
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