package com.newton.shared_ui.sharedComponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.paging.*
import androidx.paging.compose.*
import com.newton.domain.models.FilmData

@Composable
fun FilmListView(
    modifier: Modifier = Modifier,
    filmItems: LazyPagingItems<FilmData>,
    onRetry: () -> Unit,
    onFilmClick: (Int?) -> Unit = {},
    enabled: Boolean
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 15.dp)
    ) {
        when (filmItems.loadState.refresh) {
            is LoadState.Error -> {
                item {
                    ErrorScreen(
                        message = "Could not load trending shows",
                        onRetry = { onRetry() },
                        titleText = "TV SHOWS",
                    )
                }
            }

            is LoadState.Loading -> {
                items(5) {
                    FilmShimmerCard(
                    )
                }
            }

            else -> {
                items(filmItems.itemCount) { index ->
                    val tvShow = filmItems[index]
                    if (tvShow != null) {
                        FilmCard(
                            id = tvShow.id,
                            posterPath = tvShow.posterPath ?: "",
                            title = tvShow.name ?: tvShow.title ?: "Unknown title",
                            onClick = onFilmClick,
                            enabled = enabled,
                        )
                    }
                }

                if (filmItems.loadState.append is LoadState.Loading) {
                    item {
                        FilmShimmerCard(
                        )
                    }
                }
            }
        }
    }
}

