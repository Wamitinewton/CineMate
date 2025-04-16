package com.newton.movies.presentation.view.movieDetails

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.domain.models.ProductionCompany
import com.newton.shared_ui.sharedComponents.*

@Composable
fun ProductionSection(companies: List<ProductionCompany>) {
    val visibleCompanies = companies.filter { !it.name.isNullOrBlank() }.take(3)
    if (visibleCompanies.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
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