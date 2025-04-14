package com.newton.movies.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.enums.ErrorType
import com.newton.movies.presentation.events.MovieDetailsEvents
import com.newton.movies.presentation.state.MoviesDetailsUiState
import com.newton.network.domain.repositories.FilmDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val filmDetailsRepository: FilmDetailsRepository
) : ViewModel() {

    private val _movieDetailsUiState =
        MutableStateFlow<MoviesDetailsUiState>(MoviesDetailsUiState.Initial)
    val movieDetailsUiState: StateFlow<MoviesDetailsUiState> = _movieDetailsUiState.asStateFlow()

    fun onEvent(events: MovieDetailsEvents) {
        when (events) {
            is MovieDetailsEvents.LoadDetails -> {
                loadMovieDetails(events.id)
            }
        }
    }

    private fun loadMovieDetails(id: Int) {
        viewModelScope.launch {
            filmDetailsRepository.getMovieDetails(id).onEach { result ->
                result.handle(
                    onLoading = { isLoading ->
                        if (isLoading) {
                            _movieDetailsUiState.value = MoviesDetailsUiState.Loading
                        }
                    },
                    onError = { error, errorType, errorCode ->
                        _movieDetailsUiState.value =
                            MoviesDetailsUiState.Error(
                                message = error ?: "Unknown error occurred",
                                errorType = errorType ?: ErrorType.UNKNOWN,
                                errorCode = errorCode
                            )
                    },
                    onSuccess = { details ->
                        if (details != null) {
                            _movieDetailsUiState.value =
                                MoviesDetailsUiState.Success(
                                    moviesDetails = details
                                )
                        } else {
                            _movieDetailsUiState.value =
                                MoviesDetailsUiState.Error(
                                    message = "Movie details not found",
                                    errorType = ErrorType.UNKNOWN,
                                    errorCode = null
                                )
                        }
                    }

                        )
            }.launchIn(this)
        }
    }
}