package com.newton.reviews.presentation.viewModel

import androidx.lifecycle.*
import androidx.paging.*
import com.newton.core.enums.*
import com.newton.domain.models.*
import com.newton.domain.repository.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository
) : ViewModel() {

    fun getFilmReviews(id: Int, isMovie: Boolean): Flow<PagingData<ReviewsData>> {
        return reviewsRepository
            .getReviewsList(
                if (isMovie) ReviewsCategory.MOVIES else ReviewsCategory.SHOWS,
                id = id,
            )
            .cachedIn(viewModelScope)
    }

}