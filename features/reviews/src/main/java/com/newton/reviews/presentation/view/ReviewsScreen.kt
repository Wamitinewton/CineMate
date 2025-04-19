package com.newton.reviews.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.paging.compose.*
import com.newton.reviews.presentation.viewModel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen(
    filmId: Int,
    isMovie: Boolean,
    onBackPressed: () -> Unit,
    viewModel: ReviewsViewModel
) {
    val reviewsFlow = remember(filmId, isMovie) {
        viewModel.getFilmReviews(filmId, isMovie)
    }

    val reviews = reviewsFlow.collectAsLazyPagingItems()

    LaunchedEffect(key1 = filmId) {
        reviews.refresh()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        ReviewsContent(
            reviews = reviews,
            modifier = Modifier.padding(paddingValues)
        )
    }
}