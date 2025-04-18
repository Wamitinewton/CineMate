package com.newton.trending.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.paging.*
import androidx.paging.compose.*
import com.newton.domain.models.PeopleData
import com.newton.shared_ui.sharedComponents.*
import kotlinx.coroutines.flow.*

@Composable
fun TrendingPeopleSection(
    modifier: Modifier = Modifier,
    trendingPeopleFlow: Flow<PagingData<PeopleData>>,
    onRetry: () -> Unit,
    onPeopleDetailsClick: (Int?) -> Unit,
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
            onRetry = onRetry,
            onPeopleDetailsClick = { id ->
                onPeopleDetailsClick(id)
            }
        )
    }
}


@Composable
fun TrendingPeopleList(
    modifier: Modifier = Modifier,
    peopleItems: LazyPagingItems<PeopleData>,
    onPeopleDetailsClick: (Int?) -> Unit,
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
                    val trendingPeople = peopleItems[index]
                    if (trendingPeople != null) {
                        FilmCard(
                            posterPath = trendingPeople.profilePath ?: "",
                            title = trendingPeople.name ?: "Unknown",
                            onClick = { id ->
                                onPeopleDetailsClick(id)
                            },
                            id = trendingPeople.id,
                            enabled = true,
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
