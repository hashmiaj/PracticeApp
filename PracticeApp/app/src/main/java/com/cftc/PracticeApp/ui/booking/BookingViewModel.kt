package com.cftc.PracticeApp.ui.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.data.FakePlacesRepository
import com.cftc.PracticeApp.data.PlaceSuggestion
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookingViewModel(
    private val repo: FakePlacesRepository = FakePlacesRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(BookingUiState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        observeActiveQuery()
    }

    fun onPickupChange(v: String) {
        _state.update {
            it.copy(
                pickupText = v,
                pickupSelected = null,   // invalidate selection when user edits
                focused = Field.PICKUP
            )
        }
    }

    fun onDropoffChange(v: String) {
        _state.update {
            it.copy(
                dropoffText = v,
                dropoffSelected = null,
                focused = Field.DROPOFF
            )
        }
    }

    fun onFocus(field: Field) {
        _state.update { it.copy(focused = field) }
        showAllSuggestion()
    }

    fun clearField(field: Field) {
        _state.update {
            when (field) {
                Field.PICKUP -> it.copy(pickupText = "", pickupSelected = null)
                Field.DROPOFF -> it.copy(dropoffText = "", dropoffSelected = null)
                Field.NONE -> it
            }
        }
    }

    fun selectSuggestion(s: PlaceSuggestion) {
        _state.update {
            when (it.focused) {
                Field.PICKUP -> it.copy(
                    pickupSelected = s,
                    pickupText = s.primary,
                    suggestions = emptyList()
                )

                Field.DROPOFF -> it.copy(
                    dropoffSelected = s,
                    dropoffText = s.primary,
                    suggestions = emptyList()
                )

                Field.NONE -> it // ignore if no focus
            }
        }
    }

    fun showAllSuggestion() {
        viewModelScope.launch {
            _state.update {
                when (it.focused) {
                    Field.PICKUP -> {
                        if (it.activeQuery.isBlank()) {
                            it.copy(suggestions = repo.getAllPlaces())
                        } else it
                    }
                    Field.DROPOFF -> {
                        if (it.activeQuery.isBlank()) {
                            it.copy(suggestions = repo.getAllPlaces())
                        } else it
                    }
                    Field.NONE -> it
                }
            }
        }
    }

    fun swapLocations() {
        _state.update {
            it.copy(
                pickupText = it.dropoffText,
                dropoffText = it.pickupText,
                pickupSelected = it.dropoffSelected,
                dropoffSelected = it.pickupSelected
            )
        }
    }

    fun retry() {
        // Re-run the latest search using activeQuery
        observeActiveQuery(forceOnce = true)
    }

    /**
     * Debounced suggestions pipeline.
     * TODO: call from init() after you’re ready, or from UI when user starts typing.
     */
    fun observeActiveQuery(forceOnce: Boolean = false) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            state
                .map { it.activeQuery }
                .debounce(300)
                .distinctUntilChanged()
                .collect { q ->
                    if (q.isBlank() && !forceOnce) {
                        _state.update {
                            it.copy(
                                suggestions = emptyList(),
                                error = null,
                                isLoading = false
                            )
                        }
                        return@collect
                    }
                    _state.update { it.copy(isLoading = true, error = null) }
                    runCatching { repo.search(q) }
                        .onSuccess { results ->
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    suggestions = results,
                                    error = null
                                )
                            }
                        }
                        .onFailure { e ->
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = e.message ?: "Unknown error"
                                )
                            }
                        }
                }
        }
    }
}