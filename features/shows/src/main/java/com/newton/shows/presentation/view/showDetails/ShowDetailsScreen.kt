package com.newton.shows.presentation.view.showDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.shared_ui.sharedComponents.CustomLoadingIndicator
import com.newton.shared_ui.sharedComponents.ErrorScreen
import com.newton.shared_ui.sharedComponents.FavoriteButton
import com.newton.shared_ui.theme.backgroundGradient
import com.newton.shows.presentation.events.ShowsDetailsEvents
import com.newton.shows.presentation.state.ShowsDetailsUiState
import com.newton.shows.presentation.viewModel.ShowsDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDetailsScreen(
    viewModel: ShowsDetailsViewModel,
    seriesId: Int,
    onBackClick: () -> Unit
) {
    LaunchedEffect(seriesId) {
        viewModel.onEvent(ShowsDetailsEvents.LoadDetails(seriesId))
    }

    val seriesDetailsState = viewModel.seriesDetailsUiState.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    val topBarHeight = 64.dp
    val topBarAlpha = remember { mutableFloatStateOf(0f) }
    topBarAlpha.floatValue = (scrollState.value / 300f).coerceIn(0f, 1f)

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
                            when (seriesDetailsState) {
                                is ShowsDetailsUiState.Success -> {
                                    Text(
                                        text = seriesDetailsState.moviesDetails.name ?: "",
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
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                actions = {
                    when (seriesDetailsState) {
                        is ShowsDetailsUiState.Success -> {
                            FavoriteButton(
                                isFavorite = false,
                                onToggle = { }
                            )
                        }

                        else -> {}
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
        floatingActionButton = {
            when (seriesDetailsState) {
                is ShowsDetailsUiState.Success -> {
                    FloatingActionButton(
                        onClick = { },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play trailer"
                        )
                    }
                }

                else -> {}
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundGradient)
        ) {
            when (seriesDetailsState) {
                is ShowsDetailsUiState.Loading -> {
                    CustomLoadingIndicator(text = "Loading Movie Details..")
                }

                is ShowsDetailsUiState.Error -> {
                    ErrorScreen(
                        message = seriesDetailsState.message,
                        onRetry = { viewModel.onEvent(ShowsDetailsEvents.LoadDetails(seriesId)) }
                    )
                }

                is ShowsDetailsUiState.Success -> {
                    ShowsDetailsContent(
                        filmDetails = seriesDetailsState.moviesDetails,
                        scrollState = scrollState
                    )
                }

                else -> { /* Initial state */
                }
            }
        }
    }
}