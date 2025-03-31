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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val trendingRepository: TrendingRepository,
    private val prefsRepository: PrefsRepository
): ViewModel() {

    private val allowAdultContent: Boolean = prefsRepository.getAllowAdultContent()

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            refreshTrigger.emit(Unit)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val trendingShows: Flow<PagingData<FilmData>> = refreshTrigger
        .flatMapLatest {
            trendingRepository.getTrendingShows(allowAdult = allowAdultContent)
        }
        .cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val allTrendingFilms: Flow<PagingData<FilmData>> = refreshTrigger
        .flatMapLatest {
            trendingRepository.getAllTrendingFilms()
        }
        .cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val trendingMovies: Flow<PagingData<FilmData>> = refreshTrigger
        .flatMapLatest {
            trendingRepository.getTrendingMovies()
        }
        .cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val trendingPeople: Flow<PagingData<PeopleData>> = refreshTrigger
        .flatMapLatest {
            trendingRepository.getTrendingPeople()
        }
        .cachedIn(viewModelScope)
}