package com.newton.movies.presentation.view.movieDetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.core.utils.*
import com.newton.domain.models.FilmDetails
import com.newton.shared_ui.sharedComponents.*

@Composable
fun KeyDetailsSection(movie: FilmDetails) {

    val cardGradient = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.85f),
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
        )
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        SectionHeading("Details")

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = cardGradient)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DetailRow("Budget", formatCurrency(movie.budget), Icons.Default.AttachMoney)

                    DetailRow(
                        "Revenue",
                        formatCurrency(movie.revenue),
                        Icons.Default.MonetizationOn
                    )

                    if (movie.status != null) {
                        DetailRow("Status", movie.status, Icons.Default.Info)
                    }

                    if (!movie.originalTitle.isNullOrBlank() && movie.originalTitle != movie.title) {
                        DetailRow("Original Title", movie.originalTitle, Icons.Default.Title)
                    }

                    if (!movie.imdbId.isNullOrBlank()) {
                        DetailRow("IMDB", "View on IMDB", Icons.AutoMirrored.Filled.OpenInNew, true)
                    }

                    if (!movie.homepage.isNullOrBlank()) {
                        DetailRow("Website", "Visit Official Site", Icons.Default.Language, true)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String?,
    icon: ImageVector,
    isLink: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = value ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = if (isLink) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = if (isLink) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}