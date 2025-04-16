package com.newton.trending.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.paging.*
import androidx.paging.compose.*
import com.newton.domain.models.FilmData
import com.newton.shared_ui.sharedComponents.*
import kotlinx.coroutines.flow.*

@Composable
fun TrendingMoviesSection(
    modifier: Modifier = Modifier,
    trendingMoviesFlow: Flow<PagingData<FilmData>>,
    onRetry: () -> Unit,
    onMovieClick: (Int?) -> Unit,
) {
    val moviesItems = trendingMoviesFlow.collectAsLazyPagingItems()

    Column(
        modifier = modifier
    ) {
        CategorySectionHeader(
            title = "Trending Movies",
            modifier = Modifier.padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        TrendingFilmList(
            modifier = modifier.fillMaxWidth(),
            filmItems = moviesItems,
            onRetry = onRetry,
            onMovieClick = { id ->
                onMovieClick(id)
            },
            isMovie = true,
            enabled = true
        )
    }
}

