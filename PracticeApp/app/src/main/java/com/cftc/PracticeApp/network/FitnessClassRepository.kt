package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.ClassCategory
import com.cftc.PracticeApp.model.FitnessClass
import kotlinx.coroutines.delay

class FitnessClassRepository() {
    suspend fun getClasses(): List<FitnessClass> {
        delay(2000)
        return listOf(
            // Mon, Sep 8
            FitnessClass(
                "1",
                "Morning Flow Yoga",
                ClassCategory.Yoga,
                "2025-09-08",
                "6:30",
                "7:15",
                "Sarah Johnson",
                null,
                false
            )
        )
    }
}