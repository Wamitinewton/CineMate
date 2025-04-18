package com.newton.movies.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.newton.core.enums.MovieCategory
import com.newton.domain.models.FilmData
import com.newton.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModel() {

    val nowPlayingMovieList: Flow<PagingData<FilmData>> = moviesRepository
        .getListOfMovies(MovieCategory.NOW_PLAYING)
        .cachedIn(viewModelScope)

    val popularMovieList: Flow<PagingData<FilmData>> = moviesRepository
        .getListOfMovies(MovieCategory.POPULAR)
        .cachedIn(viewModelScope)

    val topRatedMovieList: Flow<PagingData<FilmData>> = moviesRepository
        .getListOfMovies(MovieCategory.TOP_RATED)
        .cachedIn(viewModelScope)

    val upcomingMovieList: Flow<PagingData<FilmData>> = moviesRepository
        .getListOfMovies(MovieCategory.UPCOMING)
        .cachedIn(viewModelScope)

}
