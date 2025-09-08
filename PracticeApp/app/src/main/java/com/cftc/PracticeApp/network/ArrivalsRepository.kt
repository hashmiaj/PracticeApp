package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.Flight
import com.cftc.PracticeApp.model.FlightStatus
import kotlinx.coroutines.delay

class ArrivalsRepository() {
    suspend fun getAllArrivals(): List<Flight> {
        delay(2000)
        return listOf(
            Flight(
                "1",
                "Alaska",
                "1247",
                "Seattle",
                "SEA",
                "12:12",
                FlightStatus.OnTime,
                false
            )
        )
    }
}