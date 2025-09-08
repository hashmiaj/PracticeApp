package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.Flight
import com.cftc.PracticeApp.model.FlightStatus

data class ArrivalsUiState(
    val arrivalsList: List<Flight> = emptyList(),
    val isLoading: Boolean = false,
    val selected: FlightStatus = FlightStatus.All,
    val inputQuery: String = "",
    val appliedQuery: String = "",
) {
    private val filteredList: List<Flight>
        get() = if (selected == FlightStatus.All) arrivalsList else arrivalsList.filter { it.status == selected }

    val visibleList: List<Flight>
        get() = if (appliedQuery.isBlank()) filteredList else filteredList.filter {
            it.matches(
                appliedQuery
            )
        }

    val showEmpty: Boolean
        get() = visibleList.isEmpty() && !isLoading
}

fun Flight.matches(q: String): Boolean {
    return airline.contains(q, ignoreCase = true) || flightNumber.contains(
        q,
        ignoreCase = true
    ) || city.contains(q, ignoreCase = true)
}