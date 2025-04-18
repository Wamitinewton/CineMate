package com.newton.people.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.domain.models.PeopleDetails

@Composable
fun PersonInfoSection(personDetails: PeopleDetails) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Personal Information",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    personDetails.birthday?.let {
                        InfoItem(
                            icon = Icons.Filled.Cake,
                            label = "Birthday",
                            value = it
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    personDetails.placeOfBirth?.let {
                        InfoItem(
                            icon = Icons.Filled.LocationOn,
                            label = "Place of Birth",
                            value = it
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    personDetails.popularity?.let {
                        InfoItem(
                            icon = Icons.Filled.Star,
                            label = "Popularity",
                            value = "$it"
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    personDetails.knownForDepartment?.let {
                        InfoItem(
                            icon = Icons.Filled.Movie,
                            label = "Department",
                            value = it
                        )
                    }
                }
            }
        }
    }
}
