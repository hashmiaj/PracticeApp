package com.cftc.PracticeApp.model

enum class FlightStatus {All, OnTime, Delayed, Landed}

data class Flight(
    val id : String,
    val airline: String,
    val flightNumber: String,
    val city: String,
    val cityCode: String,
    val time: String,
    val status: FlightStatus,
    val isFavorite: Boolean,
)