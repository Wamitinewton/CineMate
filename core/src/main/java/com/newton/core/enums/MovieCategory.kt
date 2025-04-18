package com.newton.core.enums

enum class MovieCategory(val endpoint: String, id: Int? = null) {
    TRENDING("trending/movie/day"),
    NOW_PLAYING("movie/now_playing"),
    POPULAR("movie/popular"),
    TOP_RATED("movie/top_rated"),
    UPCOMING("movie/upcoming"),
    SIMILAR ("movie/{movie_id}/similar")
}