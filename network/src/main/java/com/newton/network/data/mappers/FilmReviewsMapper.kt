package com.newton.network.data.mappers

import com.newton.domain.models.AuthorDetails
import com.newton.domain.models.ReviewsData
import com.newton.network.data.dto.AuthorDetailsDto
import com.newton.network.data.dto.ReviewsDataDto

fun ReviewsDataDto.toReviewDomain(): ReviewsData {
    return ReviewsData(
        author = author,
        authorDetails = author_details?.toAuthorDetails(),
        content = content,
        createdAt = created_at,
        id = id,
        updatedAt = updated_at,
        url = url
    )
}

fun AuthorDetailsDto.toAuthorDetails(): AuthorDetails {
    return AuthorDetails(
        avatarPath = avatar_path,
        name = name,
        rating = rating,
        username = username
    )
}

fun List<ReviewsDataDto>.toReviewsList(): List<ReviewsData> =
    map { it.toReviewDomain() }