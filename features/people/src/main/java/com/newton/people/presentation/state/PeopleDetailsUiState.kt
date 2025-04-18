package com.newton.people.presentation.state

import com.newton.core.enums.ErrorType
import com.newton.domain.models.FilmDetails
import com.newton.domain.models.PeopleDetails

sealed class PeopleDetailsUiState {
    data object Initial : PeopleDetailsUiState()

    data object Loading : PeopleDetailsUiState()

    data class Success(
        val peopleDetails: PeopleDetails
    ) : PeopleDetailsUiState()

    data class Error(
        val message: String,
        val errorType: ErrorType = ErrorType.UNKNOWN,
        val errorCode: Int?
    ) : PeopleDetailsUiState()
}