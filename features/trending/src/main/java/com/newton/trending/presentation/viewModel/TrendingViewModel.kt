package com.newton.trending.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.newton.network.domain.models.FilmData
import com.newton.network.domain.models.PeopleData
import com.newton.network.domain.repositories.TrendingRepository
import com.newton.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val trendingRepository: TrendingRepository,
    private val prefsRepository: PrefsRepository
): ViewModel() {

    private val allowAdultContent: Boolean = prefsRepository.getAllowAdultContent()


    val trendingShows: Flow<PagingData<FilmData>> = trendingRepository
        .getTrendingShows(allowAdult =allowAdultContent)
        .cachedIn(viewModelScope)

    val allTrendingFilms: Flow<PagingData<FilmData>> = trendingRepository
        .getAllTrendingFilms()
        .cachedIn(viewModelScope)

    val trendingMovies: Flow<PagingData<FilmData>> = trendingRepository
        .getTrendingMovies()
        .cachedIn(viewModelScope)

    val trendingPeople: Flow<PagingData<PeopleData>> = trendingRepository
        .getTrendingPeople()
        .cachedIn(viewModelScope)

}