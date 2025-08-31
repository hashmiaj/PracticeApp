package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.AiModel
import com.cftc.PracticeApp.model.ModelCategory

data class AiModelsUiState(
    val items: List<AiModel> = emptyList(),
    val selected: ModelCategory = ModelCategory.All,
    val isLoading: Boolean = false,
    val error: Throwable? = null
) {
    val visible: List<AiModel>
        get() = if (selected == ModelCategory.All) items
        else items.filter { it.modelCategory == selected }

    val showEmpty: Boolean get() = !isLoading && error == null && visible.isEmpty()
}