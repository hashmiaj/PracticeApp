package com.cftc.PracticeApp.data

data class PlaceSuggestion(
    val id: String,
    val primary: String,   // e.g., "SFO International Airport"
    val secondary: String, // e.g., "San Francisco, CA"
    val type: Type = Type.GENERAL
) {
    enum class Type { SAVED, RECENT, NEARBY, GENERAL }
}