package com.newton.trending.presentation.view

import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.newton.network.domain.models.FilmData
import com.newton.shared_ui.components.ErrorScreen
import com.newton.shared_ui.components.NetworkImage
import com.newton.shared_ui.components.shimmerEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun AllTrendingSection(
    trendingShowsFlow: Flow<PagingData<FilmData>>,
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
            modifier = modifier.fillMaxWidth()
        ) {
            when (trendingShows.loadState.refresh) {
                is LoadState.Loading -> AllTrendingCarouselShimmer(modifier)
                is LoadState.Error ->
                    ErrorScreen(
                        message = "Failed to load trending shows. Try again",
                        onRetry = {
                            trendingShows.refresh()
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