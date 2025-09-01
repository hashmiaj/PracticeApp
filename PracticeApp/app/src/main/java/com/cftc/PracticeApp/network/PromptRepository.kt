package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.Prompt
import com.cftc.PracticeApp.model.PromptCategory
import kotlinx.coroutines.delay

class PromptRepository() {
    suspend fun getPrompts(): List<Prompt> {
        delay(2000)

        return listOf(
            // Writing
            Prompt(
                id = "1",
                title = "Professional Email Writer",
                snippet = "Create polished, professional emails for any business context",
                tag = "Business",
                category = PromptCategory.Writing,
                isFavorited = true
            ),
            Prompt(
                id = "2",
                title = "Creative Story Generator for Enhanced Narrative Development and Character Building",
                snippet = "Generate engaging stories with rich characters and compelling plots",
                tag = "Creative",
                category = PromptCategory.Writing,
                isFavorited = false
            ),
            Prompt(
                id = "3",
                title = "Blog Post Outline",
                snippet = "Structure your blog posts with compelling headlines and flow",
                tag = "Content",
                category = PromptCategory.Writing,
                isFavorited = true
            ),

            // Code
            Prompt(
                id = "4",
                title = "Code Review Assistant",
                snippet = "Analyze code for best practices, bugs, and improvements",
                tag = "Team",
                category = PromptCategory.Code,
                isFavorited = false
            ),
            Prompt(
                id = "5",
                title = "API Documentation Generator",
                snippet = "Create comprehensive API docs from code comments",
                tag = "DevOps",
                category = PromptCategory.Code,
                isFavorited = true
            ),
            Prompt(
                id = "6",
                title = "SQL Query Optimizer",
                snippet = "Optimize database queries for better performance",
                tag = "Database",
                category = PromptCategory.Code,
                isFavorited = false,
            ),

            // Analysis
            Prompt(
                id = "7",
                title = "Data Insights Extractor",
                snippet = "Extract meaningful insights from complex datasets",
                tag = "Analytics",
                category = PromptCategory.Analysis,
                isFavorited = true
            ),
            Prompt(
                id = "8",
                title = "Comprehensive Market Research Analysis and Competitive Intelligence Report Generator",
                snippet = "Analyze market trends and competitor strategies effectively",
                tag = "Business",
                category = PromptCategory.Analysis,
                isFavorited = false
            ),
            Prompt(
                id = "9",
                title = "User Feedback Analyzer",
                snippet = "Categorize and analyze user feedback for product insights",
                tag = "Product",
                category = PromptCategory.Analysis,
                isFavorited = true
            ),

            // Fun
            Prompt(
                id = "10",
                title = "Meme Generator",
                snippet = "Create funny memes and social media content",
                tag = "Social",
                category = PromptCategory.Fun,
                isFavorited = false
            ),
            Prompt(
                id = "11",
                title = "Trivia Question Creator",
                snippet = "Generate engaging trivia questions for any topic",
                tag = "Games",
                category = PromptCategory.Fun,
                isFavorited = true
            )
        )
    }
}