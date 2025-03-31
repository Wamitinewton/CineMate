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
import com.newton.shared_ui.components.SectionHeader
import kotlinx.coroutines.flow.Flow

@Composable
fun TrendingTvShowsSection(
    modifier: Modifier = Modifier,
    trendingTvShowsFlow: Flow<PagingData<FilmData>>,
    onRetry: () -> Unit
) {
    val tvShowsItems = trendingTvShowsFlow.collectAsLazyPagingItems()

    Column(
        modifier = modifier
    ) {
        SectionHeader(
            title = "Trending TV Shows",
            modifier = Modifier.padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        TrendingFilmList(
            filmItems = tvShowsItems,
            modifier = Modifier.fillMaxWidth(),
            onRetry = onRetry
        )
    }
}




