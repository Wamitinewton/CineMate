package com.newton.movies.presentation.view.movieDetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.domain.models.FilmDetails
import com.newton.shared_ui.sharedComponents.*

@Composable
fun MovieDetailsContent(
    filmDetails: FilmDetails,
    scrollState: ScrollState
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


        KeyDetailsSection(filmDetails)


        ProductionSection(companies = filmDetails.productionCompanies ?: emptyList())


        Spacer(modifier = Modifier.height(24.dp))
    }
}