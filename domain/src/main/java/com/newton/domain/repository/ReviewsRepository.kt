package com.newton.domain.repository

import androidx.paging.PagingData
import com.newton.core.enums.ReviewsCategory
import com.newton.domain.models.ReviewsData
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {

    fun getReviewsList(reviewsCategory: ReviewsCategory, id: Int): Flow<PagingData<ReviewsData>>
}