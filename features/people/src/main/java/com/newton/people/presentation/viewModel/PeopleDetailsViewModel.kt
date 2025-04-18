package com.newton.people.presentation.viewModel

import androidx.lifecycle.*
import com.newton.core.enums.*
import com.newton.domain.repository.*
import com.newton.people.presentation.events.*
import com.newton.people.presentation.state.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class PeopleDetailsViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository
) : ViewModel() {

    private val _peopleDetailsUiState =
        MutableStateFlow<PeopleDetailsUiState>(PeopleDetailsUiState.Initial)
    val peopleDetailsUiState: StateFlow<PeopleDetailsUiState> = _peopleDetailsUiState.asStateFlow()

    fun onEvent(events: PeopleDetailsEvents) {
        when (events) {
            is PeopleDetailsEvents.LoadDetails -> {
                loadMovieDetails(events.id)
            }
        }
    }

    private fun loadMovieDetails(id: Int) {
        viewModelScope.launch {
            peopleRepository.getPeopleDetails(id).onEach { result ->
                result.handle(
                    onLoading = { isLoading ->
                        if (isLoading) {
                            _peopleDetailsUiState.value = PeopleDetailsUiState.Loading
                        }
                    },
                    onError = { error, errorType, errorCode ->
                        _peopleDetailsUiState.value =
                            PeopleDetailsUiState.Error(
                                message = error ?: "Unknown error occurred",
                                errorType = errorType ?: ErrorType.UNKNOWN,
                                errorCode = errorCode
                            )
                    },
                    onSuccess = { details ->
                        if (details != null) {
                            _peopleDetailsUiState.value =
                                PeopleDetailsUiState.Success(
                                    peopleDetails = details
                                )
                        } else {
                            _peopleDetailsUiState.value =
                                PeopleDetailsUiState.Error(
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