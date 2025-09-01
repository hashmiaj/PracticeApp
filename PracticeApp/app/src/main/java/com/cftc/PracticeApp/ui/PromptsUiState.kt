package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.Prompt
import com.cftc.PracticeApp.model.PromptCategory

data class PromptsUiState(
    val promptsList: List<Prompt> = emptyList(),
    val selected: PromptCategory = PromptCategory.All,
    val inputQuery: String = "",
    val appliedQuery: String = "",
    val isLoading: Boolean = false,
) {
    private val filteredByCategory: List<Prompt>
        get() = if (selected == PromptCategory.All) promptsList
        else promptsList.filter { it.category == selected }

    val visibleList : List<Prompt>
        get () = if (appliedQuery.isBlank()) filteredByCategory
    else filteredByCategory.filter { it.matches(appliedQuery) }

    val showEmpty: Boolean get() = !isLoading && visibleList.isEmpty()

}

fun Prompt.matches(q: String) : Boolean =
    title.contains(q, ignoreCase = false)