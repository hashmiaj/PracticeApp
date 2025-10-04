package com.cftc.PracticeApp.ui.booking

import com.cftc.PracticeApp.data.PlaceSuggestion

enum class Field { NONE, PICKUP, DROPOFF }

data class BookingUiState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val pickupText: String = "",
    val dropoffText: String = "",
    val pickupSelected: PlaceSuggestion? = null,
    val dropoffSelected: PlaceSuggestion? = null,

    val focused: Field = Field.NONE,
    val suggestions: List<PlaceSuggestion> = emptyList(),

    val estFare: String? = null, // e.g., "$18–22"
    val estEta: String? = null   // e.g., "~12 min"
) {
    // TODO: refine 'activeQuery' to prefer the field in focus; fall back to non-empty field when other is selected.
    val activeQuery: String =
        when (focused) {
            Field.PICKUP -> pickupText
            Field.DROPOFF -> dropoffText
            Field.NONE -> ""
        }

    // TODO: improve confirm logic (e.g., require selected suggestions, not just non-blank text)
    val canConfirm: Boolean = pickupSelected != null && dropoffSelected != null

    val showEmptySuggestions: Boolean =
        !isLoading && error == null && activeQuery.isNotBlank() && suggestions.isEmpty()
}