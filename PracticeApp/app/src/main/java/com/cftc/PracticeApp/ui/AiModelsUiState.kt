package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.AiModel
import com.cftc.PracticeApp.model.ModelCategory

data class AiModelsUiState(
    val items: List<AiModel> = emptyList(),
    val inputQuery: String = "",     // what the user is typing (instant)
    val appliedQuery: String = "",   // debounced value we actually filter by
    val selected: ModelCategory = ModelCategory.All,
    val isLoading: Boolean = false,
    val error: Throwable? = null
) {
    private val byCategory: List<AiModel>
        get() = if (selected == ModelCategory.All) items
        else items.filter { it.modelCategory == selected }

    val visible: List<AiModel>
        get() = if (appliedQuery.isBlank()) byCategory
        else byCategory.filter { it.matches(appliedQuery) }

    val showEmpty: Boolean get() = !isLoading && error == null && visible.isEmpty()
}

fun AiModel.matches(q: String): Boolean =
    name.contains(q, ignoreCase = true)