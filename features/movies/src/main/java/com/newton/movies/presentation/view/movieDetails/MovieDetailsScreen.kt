package com.newton.movies.presentation.view.movieDetails

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.*
import com.newton.movies.presentation.events.*
import com.newton.movies.presentation.state.*
import com.newton.movies.presentation.viewModel.*
import com.newton.shared_ui.sharedComponents.*
import com.newton.shared_ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movieDetailsViewModel: MovieDetailsViewModel,
    movieListViewModel: MovieListViewModel,
    movieId: Int,
    onBackClick: () -> Unit,
    onSimilarMovieClick: (Int) -> Unit,
) {
    LaunchedEffect(movieId) {
        movieDetailsViewModel.onEvent(MovieDetailsEvents.LoadDetails(movieId))
    }

    val similarMoviesFlow = remember(movieId) {
        movieListViewModel.getSimilarMovies(movieId)
    }

    val movieDetailsState =
        movieDetailsViewModel.movieDetailsUiState.collectAsStateWithLifecycle().value
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
                            when (movieDetailsState) {
                                is MoviesDetailsUiState.Success -> {
                                    Text(
                                        text = movieDetailsState.moviesDetails.title ?: "",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        style = MaterialTheme.typography.titleMedium
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
                    when (movieDetailsState) {
                        is MoviesDetailsUiState.Success -> {
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
            when (movieDetailsState) {
                is MoviesDetailsUiState.Success -> {
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
            when (movieDetailsState) {
                is MoviesDetailsUiState.Loading -> {
                    CustomLoadingIndicator(text = "Loading Movie Details..")
                }

                is MoviesDetailsUiState.Error -> {
                    ErrorScreen(
                        message = movieDetailsState.message,
                        onRetry = {
                            movieDetailsViewModel.onEvent(
                                MovieDetailsEvents.LoadDetails(
                                    movieId
                                )
                            )
                        }
                    )
                }

                is MoviesDetailsUiState.Success -> {
                    MovieDetailsContent(
                        filmDetails = movieDetailsState.moviesDetails,
                        scrollState = scrollState,
                        similarMovies = similarMoviesFlow,
                        onSimilarMovieClick = { id ->
                            onSimilarMovieClick(id!!)
                        }
                    )
                }

                else -> { /* Initial state */
                }
            }
        }
    }
}