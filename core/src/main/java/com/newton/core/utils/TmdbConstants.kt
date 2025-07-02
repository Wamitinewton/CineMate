package com.newton.core.utils

object TmdbConstants {
    const val TMDB_AUTH_BASE_URL = "https://www.themoviedb.org"
    const val TMDB_AUTH_CALLBACK_SCHEME = "cinemate"
    const val TMDB_AUTH_CALLBACK_HOST = "auth"
    const val TMDB_AUTH_CALLBACK_URL = "$TMDB_AUTH_CALLBACK_SCHEME://$TMDB_AUTH_CALLBACK_HOST"

    fun getAuthUrl(requestToken: String): String {
        return "$TMDB_AUTH_BASE_URL/authenticate/$requestToken?redirect_to=$TMDB_AUTH_CALLBACK_URL"
    }
}