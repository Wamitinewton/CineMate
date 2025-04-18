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
fun TrendingTvShowsSection(
    modifier: Modifier = Modifier,
    trendingTvShowsFlow: Flow<PagingData<FilmData>>,
    onRetry: () -> Unit,
    onShowsClick: (Int?) -> Unit,
) {
    val tvShowsItems = trendingTvShowsFlow.collectAsLazyPagingItems()

    Column(
        modifier = modifier
    ) {
        CategorySectionHeader(
            title = "Trending TV Shows",
            modifier = Modifier.padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        FilmListView(
            filmItems = tvShowsItems,
            modifier = Modifier.fillMaxWidth(),
            onRetry = onRetry,
            onFilmClick = { id ->
                onShowsClick(id)
            },
            enabled = true
        )
    }
}




