package com.newton.people.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.domain.models.*
import com.newton.shared_ui.sharedComponents.MediaHeroSection

@Composable
fun PersonDetailsContent(personDetails: PeopleDetails, scrollState: ScrollState) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        MediaHeroSection(
            backdropPath = personDetails.profilePath,
            posterPath = personDetails.profilePath,
            title = personDetails.name,
            showReviewCard = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            PersonInfoSection(personDetails)

            Spacer(modifier = Modifier.height(24.dp))

            if (!personDetails.biography.isNullOrEmpty()) {
                BiographySection(personDetails.biography ?: "")
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (!personDetails.alsoKnownAs.isNullOrEmpty()) {
                AlsoKnownAsSection(personDetails.alsoKnownAs ?: emptyList())
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}