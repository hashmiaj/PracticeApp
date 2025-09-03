package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.Dish
import com.cftc.PracticeApp.model.DishCategory
import kotlinx.coroutines.delay

class DishesRepository() {
    suspend fun getAllDishes() : List<Dish> {
        delay(2000)
        return listOf(
            Dish("1","Crispy Calamari","Fresh squid rings with marinara.", DishCategory.Appetizers, 12.99, null),
            Dish("2","Bruschetta Trio","Three varieties of our signature bruschetta.", DishCategory.Appetizers, 9.99, null),
            Dish("3","Buffalo Wings","Tossed in buffalo sauce.", DishCategory.Appetizers, 14.99, null),
            Dish("4","Grilled Salmon","Lemon herb butter.", DishCategory.Mains, 24.99, null),
            Dish("5","Ribeye Steak","Garlic mash.", DishCategory.Mains, 32.99, null),
            Dish("6","Chicken Parmesan","Marinara & mozzarella.", DishCategory.Mains, 19.99, null),
            Dish("7","Cheesecake","Classic NY style.", DishCategory.Desserts, 6.50, null),
            Dish("8","Mint Lemonade","House-made.", DishCategory.Drinks, 3.75, null),
        )
    }
}