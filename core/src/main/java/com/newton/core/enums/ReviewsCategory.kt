package com.newton.core.enums

enum class ReviewsCategory(val endpoint: String, id: Int? = null) {
    MOVIES("movie/{movie_id}/reviews"),
    SHOWS("tv/{series_id}/reviews"),
}