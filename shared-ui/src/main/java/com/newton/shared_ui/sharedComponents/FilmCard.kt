package com.newton.shared_ui.sharedComponents

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*

@Composable
fun FilmCard(
    posterPath: String,
    title: String,
    onClick: (Int?) -> Unit = {},
    id: Int? = null,
    enabled: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(150.dp)
    ) {
        Card(
            enabled = enabled,
            onClick = { onClick(id) },
            modifier = Modifier
                .height(225.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            NetworkImage(
                modifier = Modifier.fillMaxSize(),
                imageUrl = posterPath,
                contentDescription = "shows"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}