package com.newton.shows.presentation.viewModel

import androidx.lifecycle.*
import com.newton.core.enums.*
import com.newton.domain.repository.ShowsRepository
import com.newton.shows.presentation.events.ShowsDetailsEvents
import com.newton.shows.presentation.state.ShowsDetailsUiState
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class ShowsDetailsViewModel @Inject constructor(
    private val showsRepository: ShowsRepository
) : ViewModel() {

    private val _seriesDetailsUiState =
        MutableStateFlow<ShowsDetailsUiState>(ShowsDetailsUiState.Initial)
    val seriesDetailsUiState: StateFlow<ShowsDetailsUiState> = _seriesDetailsUiState.asStateFlow()

    fun onEvent(events: ShowsDetailsEvents) {
        when (events) {
            is ShowsDetailsEvents.LoadDetails -> {
                loadMovieDetails(events.id)
            }
        }
    }

    private fun loadMovieDetails(id: Int) {
        viewModelScope.launch {
            showsRepository.getShowsDetails(id).onEach { result ->
                result.handle(
                    onLoading = { isLoading ->
                        if (isLoading) {
                            _seriesDetailsUiState.value = ShowsDetailsUiState.Loading
                        }
                    },
                    onError = { error, errorType, errorCode ->
                        _seriesDetailsUiState.value =
                            ShowsDetailsUiState.Error(
                                message = error ?: "Unknown error occurred",
                                errorType = errorType ?: ErrorType.UNKNOWN,
                                errorCode = errorCode
                            )
                    },
                    onSuccess = { details ->
                        if (details != null) {
                            _seriesDetailsUiState.value =
                                ShowsDetailsUiState.Success(
                                    moviesDetails = details
                                )
                        } else {
                            _seriesDetailsUiState.value =
                                ShowsDetailsUiState.Error(
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