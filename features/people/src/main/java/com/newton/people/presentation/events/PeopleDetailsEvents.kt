package com.newton.people.presentation.events

sealed class PeopleDetailsEvents {
    data class LoadDetails(val id: Int) : PeopleDetailsEvents()
}