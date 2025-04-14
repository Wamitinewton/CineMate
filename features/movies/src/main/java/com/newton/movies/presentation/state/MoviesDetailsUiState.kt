package com.newton.movies.presentation.state

import com.newton.core.enums.ErrorType
import com.newton.network.domain.models.MovieDetails

sealed class MoviesDetailsUiState {
    data object Initial : MoviesDetailsUiState()

    data object Loading : MoviesDetailsUiState()

    data class Success(
        val moviesDetails: MovieDetails
    ) : MoviesDetailsUiState()

    data class Error(
        val message: String,
        val errorType: ErrorType = ErrorType.UNKNOWN,
        val errorCode: Int?
    ) : MoviesDetailsUiState()
}