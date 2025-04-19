package com.newton.reviews.presentation.view

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.paging.*
import androidx.paging.compose.*
import com.newton.domain.models.*
import com.newton.shared_ui.sharedComponents.*
import com.newton.shared_ui.theme.backgroundGradient

@Composable
fun ReviewsContent(
    reviews: LazyPagingItems<ReviewsData>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize().background(backgroundGradient)) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                AnimatedVisibility(
                    visible = reviews.itemCount > 0,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "${reviews.itemCount} Review${if (reviews.itemCount != 1) "s" else ""}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }

            items(
                items = reviews,
                key = { it.id ?: it.hashCode().toString() }
            ) { review ->
                review?.let {
                    ReviewCard(review = it)
                }
            }

            item {
                if (reviews.loadState.append is LoadState.Loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp
                        )
                    }
                }
            }

            if (reviews.itemCount == 0 && reviews.loadState.refresh is LoadState.NotLoading) {
                item {
                    EmptyReviewsMessage()
                }
            }
        }

        AnimatedVisibility(
            visible = reviews.loadState.refresh is LoadState.Loading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            CustomLoadingIndicator(text = "Loading reviews...")
        }

        AnimatedVisibility(
            visible = reviews.loadState.refresh is LoadState.Error,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                ErrorScreen(
                    message = "Failed to Load REVIEWS",
                    onRetry = {
                        reviews.refresh()
                    },
                    titleText = "Error",
                )
            }
        }
    }
}