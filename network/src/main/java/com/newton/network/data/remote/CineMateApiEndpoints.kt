package com.newton.network.data.remote

object CineMateApiEndpoints {
    object Trending {
        const val ALL_TRENDING = "trending/all/day"
        const val PEOPLE_TRENDING = "trending/person/day"
    }

    object Movies {
        const val MOVIE_DETAILS = "movie/{movie_id}"
    }

    object Shows {
        const val SERIES_DETAILS = "tv/{series_id}"
    }
}