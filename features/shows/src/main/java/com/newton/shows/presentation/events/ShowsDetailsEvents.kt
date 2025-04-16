package com.newton.shows.presentation.events

sealed class ShowsDetailsEvents {
    data class LoadDetails(val id: Int) : ShowsDetailsEvents()
}