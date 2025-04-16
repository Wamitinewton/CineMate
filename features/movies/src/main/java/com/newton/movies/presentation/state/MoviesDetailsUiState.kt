package com.newton.movies.presentation.state

import com.newton.core.enums.*
import com.newton.domain.models.FilmDetails

sealed class MoviesDetailsUiState {
    data object Initial : MoviesDetailsUiState()

    data object Loading : MoviesDetailsUiState()

    data class Success(
        val moviesDetails: FilmDetails
    ) : MoviesDetailsUiState()

    data class Error(
        val message: String,
        val errorType: ErrorType = ErrorType.UNKNOWN,
        val errorCode: Int?
    ) : MoviesDetailsUiState()
}