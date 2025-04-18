package com.newton.movies.presentation.view.movieDetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.paging.PagingData
import com.newton.domain.models.FilmData
import com.newton.domain.models.FilmDetails
import com.newton.shared_ui.sharedComponents.*
import kotlinx.coroutines.flow.Flow

@Composable
fun MovieDetailsContent(
    filmDetails: FilmDetails,
    scrollState: ScrollState,
    similarMovies: Flow<PagingData<FilmData>>,
    onSimilarMovieClick: (Int?) -> Unit,
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