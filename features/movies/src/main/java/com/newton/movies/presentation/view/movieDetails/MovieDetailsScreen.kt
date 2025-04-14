package com.newton.movies.presentation.view.movieDetails

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.*
import com.newton.movies.presentation.events.*
import com.newton.movies.presentation.state.*
import com.newton.movies.presentation.viewModel.*
import com.newton.shared_ui.sharedComponents.ErrorScreen
import com.newton.shared_ui.sharedComponents.FavoriteButton
import com.newton.shared_ui.theme.backgroundGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel,
    movieId: Int,
    onBackClick: () -> Unit
) {
    LaunchedEffect(movieId) {
        viewModel.onEvent(MovieDetailsEvents.LoadDetails(movieId))
    }

    val movieDetailsState = viewModel.movieDetailsUiState.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    val topBarHeight = 64.dp
    val topBarAlpha = remember { mutableFloatStateOf(0f) }
    topBarAlpha.floatValue = (scrollState.value / 300f).coerceIn(0f, 1f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AnimatedVisibility(
                        visible = topBarAlpha.floatValue > 0.3f,
                        enter = fadeIn(tween(300)),
                        exit = fadeOut(tween(300))
                    ) {
                        when (movieDetailsState) {
                            is MoviesDetailsUiState.Success -> {
                                Text(
                                    text = movieDetailsState.moviesDetails?.title ?: "",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            else -> Text("")
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    when (movieDetailsState) {
                        is MoviesDetailsUiState.Success -> {
                            FavoriteButton(
                                isFavorite = false,
                                onToggle = {  }
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
            when (movieDetailsState) {
                is MoviesDetailsUiState.Success -> {
                    FloatingActionButton(
                        onClick = {  },
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
            when (movieDetailsState) {
                is MoviesDetailsUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is MoviesDetailsUiState.Error -> {
                    ErrorScreen(
                        message = movieDetailsState.message,
                        onRetry = { viewModel.onEvent(MovieDetailsEvents.LoadDetails(movieId)) }
                    )
                }
                is MoviesDetailsUiState.Success -> {
                    MovieDetailsContent(
                        movieDetails = movieDetailsState.moviesDetails,
                        scrollState = scrollState
                    )
                }
                else -> { /* Initial state */ }
            }
        }
    }
}