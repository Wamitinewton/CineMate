package com.newton.shows.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.newton.core.enums.ReviewsCategory
import com.newton.domain.models.ReviewsData
import com.newton.domain.repository.ReviewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ShowsReviewsViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository
): ViewModel() {

    fun getShowsReviews(id: Int): Flow<PagingData<ReviewsData>> {
        return reviewsRepository
            .getReviewsList(ReviewsCategory.SHOWS, id = id)
            .cachedIn(viewModelScope)
    }
}