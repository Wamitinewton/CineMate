package com.newton.shows.presentation.view.showDetails

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
import com.newton.domain.models.FilmData
import com.newton.shared_ui.sharedComponents.CategorySectionHeader
import com.newton.shared_ui.sharedComponents.FilmListView
import kotlinx.coroutines.flow.Flow

@Composable
fun SimilarShowsSection(
    modifier: Modifier = Modifier,
    similarShows: Flow<PagingData<FilmData>>,
    onRetry: () -> Unit,
    onMovieClick: (Int?) -> Unit,
) {
    val showsItems = similarShows.collectAsLazyPagingItems()

    Column(
        modifier = modifier
    ) {
        CategorySectionHeader(
            title = "Similar Shows",
            modifier = Modifier.padding(horizontal = 15.dp),
            showSeeAllButton = false
        )

        Spacer(modifier = Modifier.height(12.dp))

        FilmListView(
            modifier = modifier.fillMaxWidth(),
            filmItems = showsItems,
            onRetry = onRetry,
            onFilmClick = { id ->
                onMovieClick(id)
            },
            enabled = true
        )
    }
}
