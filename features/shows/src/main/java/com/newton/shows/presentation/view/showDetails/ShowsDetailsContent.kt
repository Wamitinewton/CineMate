package com.newton.shows.presentation.view.showDetails

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.paging.PagingData
import com.newton.domain.models.*
import com.newton.shared_ui.sharedComponents.MediaHeroSection
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowsDetailsContent(
    filmDetails: FilmDetails,
    filmItems: Flow<PagingData<FilmData>>,
    scrollState: ScrollState,
    onSimilarShowClick: (Int?) -> Unit,
    onReviewClicked: (Int?, Boolean) -> Unit
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
                    onReviewClick = { id, isMovie ->
                        onReviewClicked(id, isMovie)
                    },
                    showId = filmDetails.id,
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

                SimilarShowsSection(
                    modifier = Modifier.fillMaxWidth(),
                    similarShows = filmItems,
                    onRetry = {},
                    onMovieClick = { id ->
                        onSimilarShowClick(id)
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
