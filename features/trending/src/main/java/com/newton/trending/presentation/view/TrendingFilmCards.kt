package com.newton.trending.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.newton.network.domain.models.FilmData
import com.newton.shared_ui.components.ErrorScreen
import com.newton.shared_ui.components.FilmCard
import com.newton.shared_ui.components.shimmerEffect

@Composable
fun TrendingFilmList(
    modifier: Modifier = Modifier,
    filmItems: LazyPagingItems<FilmData>,
    onRetry: () -> Unit
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
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            }

            else -> {
                items(filmItems.itemCount) { index ->
                    val tvShow = filmItems[index]
                    if (tvShow != null) {
                        FilmCard(
                            modifier = Modifier.padding(end = 12.dp),
                            posterPath = tvShow.posterPath ?: "",
                            title = tvShow.name ?: tvShow.title ?: "Unknown title",
                        )
                    }
                }

                if (filmItems.loadState.append is LoadState.Loading) {
                    item {
                        FilmShimmerCard(
                            modifier = Modifier.padding(end = 12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilmShimmerCard(modifier: Modifier = Modifier) {
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