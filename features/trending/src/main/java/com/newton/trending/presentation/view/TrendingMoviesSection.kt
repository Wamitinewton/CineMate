package com.newton.trending.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.newton.network.domain.models.FilmData
import com.newton.shared_ui.sharedComponents.CategorySectionHeader
import kotlinx.coroutines.flow.Flow

@Composable
fun TrendingMoviesSection(
    modifier: Modifier = Modifier,
    trendingMoviesFlow: Flow<PagingData<FilmData>>,
    onRetry: () -> Unit,
    onMovieClick: (Int?) -> Unit
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
            }
        )
    }
}

