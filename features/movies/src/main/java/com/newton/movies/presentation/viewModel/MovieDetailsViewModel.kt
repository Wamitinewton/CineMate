package com.newton.movies.presentation.viewModel

import androidx.lifecycle.*
import com.newton.core.enums.*
import com.newton.domain.repository.MoviesRepository
import com.newton.movies.presentation.events.*
import com.newton.movies.presentation.state.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
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
            moviesRepository.getMovieDetails(id).onEach { result ->
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