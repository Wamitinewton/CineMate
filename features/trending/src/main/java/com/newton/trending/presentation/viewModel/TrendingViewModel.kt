package com.newton.trending.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.newton.network.domain.models.TvShowData
import com.newton.network.domain.repositories.TrendingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val trendingRepository: TrendingRepository
): ViewModel() {

    val trendingShowsFlow: Flow<PagingData<TvShowData>> = trendingRepository
        .getTrendingShows()
        .cachedIn(viewModelScope)
}