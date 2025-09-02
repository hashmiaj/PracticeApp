package com.cftc.PracticeApp.uistate

import com.cftc.PracticeApp.model.Course
import com.cftc.PracticeApp.model.CourseCategory

data class CoursesUiState(
    val list: List<Course> = emptyList(),
    val isLoading: Boolean = false,
    val selected: CourseCategory = CourseCategory.All,
    val inputQuery: String = "",
    val appliedQuery: String = "",
) {
    val filteredList: List<Course>
        get() = if (selected == CourseCategory.All) list else list.filter { selected == it.category }

    val visibleList: List<Course>
        get() = if (appliedQuery.isBlank()) filteredList else list.filter { it.matches(appliedQuery) }

    val showEmpty: Boolean
        get() = !isLoading && visibleList.isEmpty()
}

fun Course.matches(q: String): Boolean {
    return title.contains(q, ignoreCase = true) || prof.contains(q, ignoreCase = true)
}