package com.newton.movies.presentation.view.movieDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.network.domain.models.ProductionCompany
import com.newton.shared_ui.sharedComponents.SectionHeading

@Composable
fun ProductionSection(companies: List<ProductionCompany>) {
    val visibleCompanies = companies.filter { !it.name.isNullOrBlank() }.take(3)
    if (visibleCompanies.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        SectionHeading("Production")

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            visibleCompanies.forEach { company ->
                CompanyLogo(company)
            }
        }
    }
}