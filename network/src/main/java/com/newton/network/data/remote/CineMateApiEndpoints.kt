package com.newton.network.data.remote

object CineMateApiEndpoints {
    object Trending {
        const val ALL_TRENDING = "trending/all/day"
        const val MOVIES_TRENDING = "trending/movie/day"
        const val PEOPLE_TRENDING = "trending/person/day"
        const val TV_TRENDING = "trending/tv/day"
    }

    object Movies {
        const val MOVIE_DETAILS = "movie/{movie_id}"
    }
}