package com.newton.movies.presentation.events

sealed class MovieDetailsEvents {
    data class LoadDetails(val id: Int): MovieDetailsEvents()
}