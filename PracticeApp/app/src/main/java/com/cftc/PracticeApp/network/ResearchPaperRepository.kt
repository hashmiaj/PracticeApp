package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.ResearchPaper
import kotlinx.coroutines.delay

class ResearchPaperRepository {
    suspend fun getAllResearchPapers(): List<ResearchPaper> {
        delay(2000)
        return listOf(
            ResearchPaper(
                "1",
                "Attention Is All  You Need",
                "Vaswani et al.",
                "2017",
                false
            )
        )
    }
}