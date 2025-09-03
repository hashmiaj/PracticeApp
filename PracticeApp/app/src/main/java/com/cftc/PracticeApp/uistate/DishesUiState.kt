package com.cftc.PracticeApp.uistate

import com.cftc.PracticeApp.model.Dish
import com.cftc.PracticeApp.model.DishCategory

data class DishesUiState(
    val dishesList: List<Dish> = emptyList(),
    val isLoading: Boolean = false,
    val selectedCategory: DishCategory = DishCategory.Appetizers,
) {
    val visibleList: List<Dish>
        get() = dishesList.filter { it.category == selectedCategory }
}