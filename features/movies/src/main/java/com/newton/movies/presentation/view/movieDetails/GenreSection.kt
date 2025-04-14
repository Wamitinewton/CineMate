package com.newton.movies.presentation.view.movieDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.network.domain.models.Genre
import com.newton.shared_ui.sharedComponents.FlowRow
import com.newton.shared_ui.sharedComponents.GenreChip
import com.newton.shared_ui.sharedComponents.SectionHeading

@Composable
fun GenreSection(genres: List<Genre>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        SectionHeading("Genres")

        FlowRow(
            maxItemsInEachRow = 3,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            genres.forEach { genre ->
                genre.name?.let {
                    GenreChip(name = it)
                }
            }
        }
    }

}