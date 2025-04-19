package com.newton.movies.presentation.view.movieDetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.paging.*
import com.newton.domain.models.*
import com.newton.shared_ui.sharedComponents.*
import kotlinx.coroutines.flow.*

@Composable
fun MovieDetailsContent(
    filmDetails: FilmDetails,
    scrollState: ScrollState,
    similarMovies: Flow<PagingData<FilmData>>,
    onSimilarMovieClick: (Int?) -> Unit,
    onReviewClicked: (Int?, Boolean) -> Unit,
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        MediaHeroSection(
            backdropPath = filmDetails.backdropPath,
            posterPath = filmDetails.posterPath,
            title = filmDetails.title,
            rating = filmDetails.voteAverage?.toFloat(),
            releaseYear = filmDetails.releaseDate,
            duration = filmDetails.runtime,
            tagline = filmDetails.tagline,
            onReviewClick = { id, isMovie ->
                onReviewClicked(id, isMovie)
            },
            showId = filmDetails.id
        )

        Spacer(modifier = Modifier.height(16.dp))

        QuickInfoRow(filmDetails)


        OverviewSection(overview = filmDetails.overview ?: "")


        GenreSection(genres = filmDetails.genres ?: emptyList())

        SimilarMoviesSection(
            modifier = Modifier.fillMaxWidth(),
            similarMovies = similarMovies,
            onRetry = {},
            onMovieClick = { id ->
                onSimilarMovieClick(id)
            }
        )


        Spacer(modifier = Modifier.height(24.dp))
    }
}