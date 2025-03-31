package com.newton.trending.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.newton.network.domain.models.PeopleData
import com.newton.shared_ui.components.ErrorScreen
import com.newton.shared_ui.components.FilmCard
import com.newton.shared_ui.components.SectionHeader
import kotlinx.coroutines.flow.Flow

@Composable
fun TrendingPeopleSection(
    modifier: Modifier = Modifier,
    trendingPeopleFlow: Flow<PagingData<PeopleData>>,
    onRetry: () -> Unit
) {
    val peopleItems = trendingPeopleFlow.collectAsLazyPagingItems()

    Column(
        modifier = modifier
    ) {
        SectionHeader(
            title = "Trending People",
            modifier = Modifier.padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        TrendingPeopleList(
            modifier = modifier.fillMaxWidth(),
            peopleItems = peopleItems,
            onRetry = onRetry
        )
    }
}


@Composable
fun TrendingPeopleList(
    modifier: Modifier = Modifier,
    peopleItems: LazyPagingItems<PeopleData>,
    onRetry: () -> Unit
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 15.dp)
    ) {
        when (peopleItems.loadState.refresh) {
            is LoadState.Error -> {
                item {
                    ErrorScreen(
                        message = "Could not load trending people",
                        onRetry = { peopleItems.retry() },
                        titleText = "FILM PEOPLE",
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
                items(peopleItems.itemCount) { index ->
                    val tvShow = peopleItems[index]
                    if (tvShow != null) {
                        FilmCard(
                            modifier = Modifier.padding(end = 12.dp),
                            posterPath = tvShow.profilePath ?: "",
                            title = tvShow.name ?: "Unknown",
                        )
                    }
                }

                if (peopleItems.loadState.append is LoadState.Loading) {
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
