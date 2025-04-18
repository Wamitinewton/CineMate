package com.newton.shared.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.newton.core.enums.ReviewsCategory
import com.newton.domain.models.ReviewsData
import com.newton.domain.repository.ReviewsRepository
import com.newton.network.data.mappers.toReviewsList
import com.newton.network.data.remote.FilmApiService
import com.newton.network.paging.GenericPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewsRepositoryImpl @Inject constructor(
    private val filmApiService: FilmApiService
): ReviewsRepository {
    override fun getReviewsList(
        reviewsCategory: ReviewsCategory,
        id: Int
    ): Flow<PagingData<ReviewsData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingSource(
                    apiCall = { page ->
                        val url = if (reviewsCategory == ReviewsCategory.SHOWS) {
                            reviewsCategory.endpoint.replace("{series_id}", id.toString())
                        } else {
                            reviewsCategory.endpoint.replace("{movie_id}", id.toString())
                        }

                        filmApiService.getFilmReviews(
                            page = page,
                            url = url
                        )
                    },
                    dataMapper = { response ->
                        response.results.toReviewsList()
                    },
                    getNextPageNumber = { response ->
                        if (response.results.isEmpty()) null else response.page + 1
                    }
                )
            }
        ).flow
    }
}