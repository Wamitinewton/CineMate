package com.newton.trending.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.paging.*
import androidx.paging.compose.*
import com.newton.domain.models.FilmData
import com.newton.shared_ui.sharedComponents.*

@Composable
fun TrendingFilmList(
    modifier: Modifier = Modifier,
    filmItems: LazyPagingItems<FilmData>,
    onRetry: () -> Unit,
    onMovieClick: (Int?) -> Unit = {},
    onShowsClick: (Int?) -> Unit = {},
    isMovie: Boolean,
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
                            onMovieClick = onMovieClick,
                            onShowsClick = onShowsClick,
                            isMovie = isMovie,
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

@Composable
fun FilmShimmerCard() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(150.dp)
    ) {
        Card(
            modifier = Modifier
                .height(225.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shimmerEffect()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .height(16.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )
    }
}