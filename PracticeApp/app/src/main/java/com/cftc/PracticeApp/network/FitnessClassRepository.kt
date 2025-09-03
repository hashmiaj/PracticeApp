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
            ), FitnessClass(
                "2",
                "HIIT Power 30",
                ClassCategory.HIIT,
                "2025-09-08",
                "7:30",
                "8:00",
                "Marcus Lee",
                null,
                false
            ), FitnessClass(
                "3",
                "Lunch Vinyasa",
                ClassCategory.Yoga,
                "2025-09-08",
                "12:00",
                "12:45",
                "Kira Patel",
                null,
                false
            ), FitnessClass(
                "4",
                "Cardio Groove",
                ClassCategory.Dance,
                "2025-09-08",
                "18:00",
                "18:45",
                "Lena Torres",
                null,
                false
            ),

            // Tue, Sep 9
            FitnessClass(
                "5",
                "Pilates Core Align",
                ClassCategory.Pilates,
                "2025-09-09",
                "7:00",
                "7:40",
                "Sam Park",
                null,
                false
            ), FitnessClass(
                "6",
                "Yoga Basics",
                ClassCategory.Yoga,
                "2025-09-09",
                "9:00",
                "9:45",
                "Sarah Johnson",
                null,
                false
            ), FitnessClass(
                "7",
                "HIIT Express",
                ClassCategory.HIIT,
                "2025-09-09",
                "17:30",
                "18:00",
                "Diego Ramos",
                null,
                false
            ), FitnessClass(
                "8",
                "Hip-Hop Intro",
                ClassCategory.Dance,
                "2025-09-09",
                "18:15",
                "19:00",
                "Tara Singh",
                null,
                false
            ), FitnessClass(
                "9",
                "Pilates Mat Strength",
                ClassCategory.Pilates,
                "2025-09-09",
                "19:15",
                "20:00",
                "Sam Park",
                null,
                false
            ),

            // Wed, Sep 10
            FitnessClass(
                "10",
                "Sunrise Flow",
                ClassCategory.Yoga,
                "2025-09-10",
                "6:30",
                "7:15",
                "Kira Patel",
                null,
                false
            ), FitnessClass(
                "11",
                "Power Sculpt",
                ClassCategory.HIIT,
                "2025-09-10",
                "7:30",
                "8:10",
                "Marcus Lee",
                null,
                false
            ), FitnessClass(
                "12",
                "Noon Barre Fusion",
                ClassCategory.Dance,
                "2025-09-10",
                "12:00",
                "12:40",
                "Elena Park",
                null,
                false
            ), FitnessClass(
                "13",
                "Evening Pilates Stretch",
                ClassCategory.Pilates,
                "2025-09-10",
                "18:30",
                "19:15",
                "Sam Park",
                null,
                false
            ), FitnessClass(
                "14",
                "Latin Beats",
                ClassCategory.Dance,
                "2025-09-10",
                "19:30",
                "20:15",
                "Lena Torres",
                null,
                false
            )
        )
    }
}