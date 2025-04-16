package com.newton.shows.presentation.state

import com.newton.core.enums.*
import com.newton.domain.models.FilmDetails

sealed class ShowsDetailsUiState {
    data object Initial : ShowsDetailsUiState()

    data object Loading : ShowsDetailsUiState()

    data class Success(
        val moviesDetails: FilmDetails
    ) : ShowsDetailsUiState()

    data class Error(
        val message: String,
        val errorType: ErrorType = ErrorType.UNKNOWN,
        val errorCode: Int?
    ) : ShowsDetailsUiState()
}