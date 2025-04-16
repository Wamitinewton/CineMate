package com.newton.movies.presentation.view.movieDetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.unit.*
import com.newton.domain.models.FilmDetails

@Composable
fun QuickInfoRow(movie: FilmDetails) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        movie.voteCount?.let {
            item {
                InfoPill(
                    icon = Icons.Default.HowToVote,
                    text = "$it votes"
                )
            }
        }


        if (movie.adult == true) {
            item {
                InfoPill(
                    icon = Icons.Default.Warning,
                    text = "Adult"
                )
            }
        }

        movie.originalLanguage?.let {
            item {
                InfoPill(
                    icon = Icons.Default.Language,
                    text = it.uppercase()
                )
            }
        }
    }
}


@Composable
fun InfoPill(
    icon: ImageVector,
    text: String
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}