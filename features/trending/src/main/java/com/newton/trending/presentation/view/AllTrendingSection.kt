package com.newton.trending.presentation.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.util.*
import androidx.paging.*
import androidx.paging.compose.*
import com.newton.domain.models.FilmData
import com.newton.shared_ui.sharedComponents.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.math.*


@Composable
fun AllTrendingSection(
    trendingShowsFlow: Flow<PagingData<FilmData>>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val trendingShows = trendingShowsFlow.collectAsLazyPagingItems()
    Column {
        Text(
            text = "All Trending Films",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            when (trendingShows.loadState.refresh) {
                is LoadState.Loading -> AllTrendingCarouselShimmer(modifier)
                is LoadState.Error ->
                    ErrorScreen(
                        message = "Failed to load trending shows. Try again",
                        onRetry = {
                            onRetry()
                        },
                        titleText = "TRENDING SHOWS",
                    )

                is LoadState.NotLoading -> {
                    if (trendingShows.itemCount > 0) {
                        AllTrendingCarousel(trendingShows)
                    } else {
                        AllTrendingCarouselEmpty(modifier)
                    }
                }
            }
        }
    }


}

@Composable
private fun AllTrendingCarousel(
    trendingShows: LazyPagingItems<FilmData>
) {
    val itemCount = trendingShows.itemCount
    if (itemCount == 0) return

    val pagerState = rememberPagerState(pageCount = { itemCount })
    val scope = rememberCoroutineScope()

    val isUserScrolling = remember { derivedStateOf { pagerState.isScrollInProgress } }

    LaunchedEffect(pagerState.currentPage, isUserScrolling.value) {
        if (!isUserScrolling.value) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % itemCount
            scope.launch {
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(1000, easing = EaseInOutQuart)
                )
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        flingBehavior = PagerDefaults.flingBehavior(state = pagerState)
    ) { page ->
        trendingShows[page]?.let { tvShow ->
            val title = tvShow.name ?: tvShow.title ?: ""

            val pageOffset =
                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        val scale = lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleX = scale
                        scaleY = scale

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                        rotationY = lerp(
                            start = 0f,
                            stop = 10f,
                            fraction = pageOffset.coerceIn(0f, 1f)
                        ) * if (pagerState.currentPage > page) -1 else 1
                    }
            ) {
                AllTrendingCard(
                    backdropUrl = tvShow.posterPath ?: "",
                    title = title
                )
            }
        }
    }
}

@Composable
private fun AllTrendingCard(
    backdropUrl: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        ),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            NetworkImage(
                imageUrl = backdropUrl,
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "Backdrop image for $title"
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun AllTrendingCarouselShimmer(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shimmerEffect()
        )
    }
}

@Composable
private fun AllTrendingCarouselEmpty(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No trending shows available",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}