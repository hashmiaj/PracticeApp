package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.Course
import com.cftc.PracticeApp.model.CourseCategory
import kotlinx.coroutines.delay

class CoursesRepository() {
    suspend fun getAllCourses(): List<Course> {
        delay(2000)
        return listOf(
            Course("c1", "Design Systems 101", "Ava Reed", CourseCategory.Design, 4.6, null, false),
            Course("c2", "Compose for Pros", "Liam Chen", CourseCategory.Mobile, 4.8, null, false),
            Course(
                "c3",
                "Intro to Transformers",
                "Maya Ali",
                CourseCategory.AIML,
                4.7,
                "https://images.unsplash.com/photo-1740174459699-487aec1f7bc5?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxkZXNpZ24lMjBncmFwaGljcyUyMGNyZWF0aXZlfGVufDF8fHx8MTc1NjcxMDQ2N3ww&ixlib=rb-4.1.0&q=80&w=400",
                true
            ),
            Course(
                "c4",
                "Full-Stack Web Basics",
                "Noah Patel",
                CourseCategory.Web,
                4.1,
                "https://images.unsplash.com/photo-1525338078858-d762b5e32f2c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxhcnRpZmljaWFsJTIwaW50ZWxsaWdlbmNlJTIwbWFjaGluZSUyMGxlYXJuaW5nfGVufDF8fHx8MTc1Njc0NjY5OHww&ixlib=rb-4.1.0&q=80&w=400",
                false
            ),
            Course(
                "c5",
                "Advanced Layouts in Compose",
                "Ivy Nguyen",
                CourseCategory.Mobile,
                4.5,
                "https://images.unsplash.com/photo-1730818875087-182c15e1e7a4?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxtb2JpbGUlMjBhcHAlMjBkZXZlbG9wbWVudCUyMHNtYXJ0cGhvbmV8ZW58MXx8fHwxNzU2Njk4OTk2fDA&ixlib=rb-4.1.0&q=80&w=400",
                false
            ),
            Course("c6", "Prompt Engineering", "Samir Khan", CourseCategory.AIML, 4.3, null, false),
            Course(
                "c7",
                "Design Critique Practice",
                "Elena Park",
                CourseCategory.Design,
                4.0,
                "https://images.unsplash.com/photo-1593720213681-e9a8778330a7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx3ZWIlMjBkZXZlbG9wbWVudCUyMGNvZGluZyUyMHByb2dyYW1taW5nfGVufDF8fHx8MTc1Njc3NTY1OXww&ixlib=rb-4.1.0&q=80&w=400",
                true
            ),
            Course(
                "c8",
                "Web Performance Essentials",
                "Jon Rivera",
                CourseCategory.Web,
                4.2,
                null,
                false
            ),
            Course(
                "c9",
                "On-device ML on Android",
                "Fatima Noor",
                CourseCategory.AIML,
                4.4,
                "https://images.unsplash.com/photo-1593720213681-e9a8778330a7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx3ZWIlMjBkZXZlbG9wbWVudCUyMGNvZGluZyUyMHByb2dyYW1taW5nfGVufDF8fHx8MTc1Njc3NTY1OXww&ixlib=rb-4.1.0&q=80&w=400",
                true
            ),
            Course(
                "c10",
                "Animations in Compose",
                "Zoe Martin",
                CourseCategory.Mobile,
                4.6,
                null,
                false
            ),
        )
    }
}