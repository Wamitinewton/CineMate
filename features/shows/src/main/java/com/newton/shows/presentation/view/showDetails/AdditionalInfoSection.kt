package com.newton.shows.presentation.view.showDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.domain.models.FilmDetails

@Composable
fun AdditionalInfoSection(filmDetails: FilmDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Details",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                filmDetails.genres?.let { genres ->
                    if (genres.isNotEmpty()) {
                        DetailsRow(
                            title = "Genres:",
                            content = genres.mapNotNull { it.name }.joinToString(", ")
                        )
                    }
                }

                filmDetails.type?.let { type ->
                    DetailsRow(title = "Type:", content = type)
                }

                filmDetails.originalLanguage?.let { language ->
                    DetailsRow(title = "Language:", content = language.uppercase())
                }

                if (!filmDetails.originalName.isNullOrBlank() && filmDetails.originalName != filmDetails.name) {
                    DetailsRow(title = "Original Name:", content = filmDetails.originalName ?: "")
                }

                filmDetails.originCountry?.let { countries ->
                    if (countries.isNotEmpty()) {
                        DetailsRow(
                            title = "Origin Countries:",
                            content = countries.joinToString(", ")
                        )
                    }
                }

                filmDetails.productionCountries?.let { countries ->
                    if (countries.isNotEmpty()) {
                        DetailsRow(
                            title = "Production Countries:",
                            content = countries.mapNotNull { it.name }.joinToString(", ")
                        )
                    }
                }

                filmDetails.inProduction?.let { inProduction ->
                    DetailsRow(
                        title = "In Production:",
                        content = if (inProduction) "Yes" else "No"
                    )
                }

                filmDetails.lastAirDate?.let { lastAirDate ->
                    DetailsRow(title = "Last Air Date:", content = lastAirDate)
                }

                filmDetails.homepage?.let { homepage ->
                    if (homepage.isNotBlank()) {
                        DetailsRow(title = "Homepage:", content = homepage)
                    }
                }
            }
        }
    }
}
