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
import com.newton.shared_ui.sharedComponents.ErrorScreen
import com.newton.shared_ui.sharedComponents.FilmCard
import com.newton.shared_ui.sharedComponents.CategorySectionHeader
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
        CategorySectionHeader(
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
                        onRetry = { onRetry() },
                        titleText = "FILM PEOPLE",
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
                items(peopleItems.itemCount) { index ->
                    val tvShow = peopleItems[index]
                    if (tvShow != null) {
                        FilmCard(
                            posterPath = tvShow.profilePath ?: "",
                            title = tvShow.name ?: "Unknown",
                            onClick = {},
                        )
                    }
                }

                if (peopleItems.loadState.append is LoadState.Loading) {
                    item {
                        FilmShimmerCard(
                        )
                    }
                }
            }
        }
    }
}
