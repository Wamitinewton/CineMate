package com.newton.movies.presentation.view.movieDetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.network.domain.models.*
import com.newton.shared_ui.sharedComponents.*

@Composable
fun MovieDetailsContent(
    movieDetails: MovieDetails,
    scrollState: ScrollState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        MediaHeroSection(
            backdropPath = movieDetails.backdropPath,
            posterPath = movieDetails.posterPath,
            title = movieDetails.title,
            rating = movieDetails.voteAverage?.toFloat(),
            releaseYear = movieDetails.releaseDate,
            duration = movieDetails.runtime,
            tagline = movieDetails.tagline,
        )

        Spacer(modifier = Modifier.height(16.dp))

        QuickInfoRow(movieDetails)


        OverviewSection(overview = movieDetails.overview ?: "")


        GenreSection(genres = movieDetails.genres ?: emptyList())


        KeyDetailsSection(movieDetails)


        ProductionSection(companies = movieDetails.productionCompanies ?: emptyList())


        Spacer(modifier = Modifier.height(24.dp))
    }
}