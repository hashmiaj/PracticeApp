package com.cftc.PracticeApp.uistate

import com.cftc.PracticeApp.model.ClassCategory
import com.cftc.PracticeApp.model.FitnessClass

data class FitnessClassUiState(
    val classList: List<FitnessClass> = emptyList(),
    val isLoading: Boolean = false,
    val selected: ClassCategory = ClassCategory.All,
) {
    val visibleList: List<FitnessClass>
        get() = if (selected == ClassCategory.All) classList
        else classList.filter { it.category == selected }

    val showEmpty: Boolean
        get() = visibleList.isEmpty()
}