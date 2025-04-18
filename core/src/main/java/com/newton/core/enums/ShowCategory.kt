package com.newton.core.enums

enum class ShowCategory(val endpoint: String) {
    TRENDING("trending/tv/day"),
    POPULAR("tv/popular"),
    TOP_RATED("tv/top_rated"),
    ON_THE_AIR("tv/on_the_air"),
    AIRING_TODAY("tv/airing_today"),
    SIMILAR ("tv/{series_id}/similar")

}
