package com.cftc.PracticeApp.data

import kotlinx.coroutines.delay

class FakeConversationsRepository {

    // Simulate network/db
    suspend fun loadConversations(): List<Conversation> {
        delay(600) // network-ish latency
        val now = System.currentTimeMillis()
        return listOf(
            Conversation("1","Maya Ali","See you at 5?", now - 3_000, 2,"MA", true),
            Conversation("2","Team – Android","Please review the PR before EOD", now - 60_000, 0,"TA"),
            Conversation("3","Mom","Beta, did you eat?", now - 120_000, 1,"M"),
            Conversation("4","Zaki","ship it ✅", now - 1_800_000, 0,"Z"),
            Conversation("5","Anthropic Recruiter","Sharing the starter repo details…", now - 7_200_000, 0,"AR"),
            Conversation("6","OpenAI – Panel","We’ll begin in 10 min", now - 8_400_000, 0,"OP"),
            Conversation("7","Brad (PM)","Can we move the retro?", now - 26_000_000, 3,"B"),
            Conversation("8","Spoorthy","Deck looks great", now - 52_000_000, 0,"S"),
            Conversation("9","Family Group","Photos from the trip 📸", now - 86_000_000, 0,"FG"),
            Conversation("10","Giants Fans","Game tonight?", now - 100_000_000, 0,"GF"),
            Conversation("11","MECA Team","New event feed is live!", now - 160_000_000, 0,"MT"),
            Conversation("12","CFTC Board","Slides for Friday?", now - 300_000_000, 1,"CB")
        )
    }
}
