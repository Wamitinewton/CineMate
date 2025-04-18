package com.newton.shows.presentation.viewModel

import androidx.lifecycle.*
import androidx.paging.*
import com.newton.core.enums.*
import com.newton.domain.models.*
import com.newton.domain.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class ShowsListViewModel @Inject constructor(
    private val showsRepository: ShowsRepository
): ViewModel() {

    val airingTodayShowsList: Flow<PagingData<FilmData>> = showsRepository
        .getListOfShows(ShowCategory.AIRING_TODAY)
        .cachedIn(viewModelScope)

    val popularShowsList: Flow<PagingData<FilmData>> = showsRepository
        .getListOfShows(ShowCategory.POPULAR)
        .cachedIn(viewModelScope)

    val topRatedShowList: Flow<PagingData<FilmData>> = showsRepository
        .getListOfShows(ShowCategory.TOP_RATED)
        .cachedIn(viewModelScope)

    val onTheAirShowList: Flow<PagingData<FilmData>> = showsRepository
        .getListOfShows(ShowCategory.ON_THE_AIR)
        .cachedIn(viewModelScope)

    fun getSimilarShows(seriesId: Int): Flow<PagingData<FilmData>> {
        return showsRepository
            .getListOfShows(ShowCategory.SIMILAR, id = seriesId)
            .cachedIn(viewModelScope)
    }

}