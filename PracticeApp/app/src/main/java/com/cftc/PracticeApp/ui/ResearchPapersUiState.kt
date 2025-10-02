package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.ResearchPaper

data class ResearchPapersUiState(
    val listOfPapers: List<ResearchPaper> = emptyList(),
    val isLoading: Boolean = false,
    val inputQuery: String = "",
    val appliedQuery: String = "",
) {
    val visibleList =
        if (!appliedQuery.isBlank()) listOfPapers.filter { it.matches(appliedQuery) } else listOfPapers
    val showEmpty = visibleList.isEmpty()
}

fun ResearchPaper.matches(q: String): Boolean {
    return title.contains(q, ignoreCase = true)
}