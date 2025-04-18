package com.newton.network.data.dto

data class FilmReviewsDto(
    val id: Int,
    val page: Int,
    val results: List<ReviewsDataDto>,
    val total_pages: Int,
    val total_results: Int
)

data class ReviewsDataDto(
    val author: String?,
    val author_details: AuthorDetailsDto?,
    val content: String?,
    val created_at: String?,
    val id: String?,
    val updated_at: String?,
    val url: String?
)

data class AuthorDetailsDto(
    val avatar_path: String?,
    val name: String?,
    val rating: Int?,
    val username: String?
)