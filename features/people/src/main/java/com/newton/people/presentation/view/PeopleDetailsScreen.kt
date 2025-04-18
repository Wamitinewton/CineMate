package com.newton.people.presentation.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.*
import com.newton.people.presentation.events.*
import com.newton.people.presentation.state.*
import com.newton.people.presentation.viewModel.*
import com.newton.shared_ui.sharedComponents.*
import com.newton.shared_ui.theme.backgroundGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleDetailsScreen(
    personId: Int,
    onNavigateBack: () -> Unit,
    viewModel: PeopleDetailsViewModel,
) {
    val uiState = viewModel.peopleDetailsUiState.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()

    val topBarHeight = 64.dp
    val topBarAlpha = remember { mutableFloatStateOf(0f) }
    topBarAlpha.floatValue = (scrollState.value / 300f).coerceIn(0f, 1f)

    LaunchedEffect(key1 = personId) {
        viewModel.onEvent(PeopleDetailsEvents.LoadDetails(personId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedVisibility(
                            visible = topBarAlpha.floatValue > 0.3f,
                            enter = fadeIn(tween(300)),
                            exit = fadeOut(tween(300))
                        ) {
                            when (uiState) {
                                is PeopleDetailsUiState.Success -> {
                                    Text(
                                        text = uiState.peopleDetails.name ?: "",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                else -> Text("")
                            }
                        }
                    }
                },

                navigationIcon = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = topBarAlpha.floatValue)
                ),
                modifier = Modifier
                    .height(topBarHeight)
                    .graphicsLayer {
                        shadowElevation = if (topBarAlpha.floatValue > 0f) 4.dp.toPx() else 0f
                    }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundGradient)
        ) {
            when (uiState) {
                is PeopleDetailsUiState.Loading -> {
                    CustomLoadingIndicator(text = "Loading Details")
                }
                is PeopleDetailsUiState.Error -> {
                    ErrorScreen(
                        message = uiState.message,
                        onRetry = {},
                        titleText = "Failed to Load PEOPLE DETAILS",
                    )
                }
                is PeopleDetailsUiState.Success -> {
                    val personDetails = uiState.peopleDetails
                    PersonDetailsContent(personDetails, scrollState)
                }
                else -> {

                }
            }
        }
    }
}