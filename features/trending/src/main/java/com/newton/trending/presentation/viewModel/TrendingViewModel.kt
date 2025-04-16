package com.newton.trending.presentation.viewModel

import androidx.lifecycle.*
import androidx.paging.*
import com.newton.domain.models.FilmData
import com.newton.domain.models.PeopleData
import com.newton.domain.repository.TrendingRepository
import com.newton.prefs.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val trendingRepository: TrendingRepository,
    private val prefsRepository: PrefsRepository
) : ViewModel() {

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