package com.newton.shows.presentation.view.showDetails

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.domain.models.*
import com.newton.shared_ui.sharedComponents.MediaHeroSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowsDetailsContent(
    filmDetails: FilmDetails,
    scrollState: ScrollState
) {
    scrollState.value > 0

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                MediaHeroSection(
                    backdropPath = filmDetails.backdropPath,
                    posterPath = filmDetails.posterPath,
                    title = filmDetails.name,
                )

                ShowInfoSection(filmDetails = filmDetails)

                if (!filmDetails.overview.isNullOrBlank()) {
                    OverviewSection(overview = filmDetails.overview ?: "")
                }

                if (!filmDetails.seasons.isNullOrEmpty()) {
                    SeasonsSection(seasons = filmDetails.seasons ?: emptyList())
                }

                if (!filmDetails.createdBy.isNullOrEmpty()) {
                    CreatorsSection(createdBy = filmDetails.createdBy ?: emptyList())
                }

                if (filmDetails.lastEpisodeToAir != null) {
                    LastEpisodeSection(lastEpisode = filmDetails.lastEpisodeToAir)
                }

                AdditionalInfoSection(filmDetails = filmDetails)

                NetworksSection(network = filmDetails.networks ?: emptyList())

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
