package com.cftc.PracticeApp.data

import kotlinx.coroutines.delay
import kotlin.random.Random

class FakePlacesRepository {

    private val base = listOf(
        PlaceSuggestion("home","Home","123 Market St, San Francisco", PlaceSuggestion.Type.SAVED),
        PlaceSuggestion("work","Work","1 Market St, San Francisco", PlaceSuggestion.Type.SAVED),
        PlaceSuggestion("sfo","SFO International Airport","San Francisco, CA", PlaceSuggestion.Type.NEARBY),
        PlaceSuggestion("oak","Oakland International Airport","Oakland, CA", PlaceSuggestion.Type.NEARBY),
        PlaceSuggestion("moscone","Moscone Center","747 Howard St, SF", PlaceSuggestion.Type.RECENT),
        PlaceSuggestion("anthropic","Anthropic HQ","555 California St, SF", PlaceSuggestion.Type.GENERAL),
        PlaceSuggestion("openai","OpenAI HQ","3180 18th St, SF", PlaceSuggestion.Type.GENERAL),
        PlaceSuggestion("berkeley","MECA Office","1101 8th St, Berkeley", PlaceSuggestion.Type.GENERAL),
        PlaceSuggestion("meta","Meta – Hacker Square","1 Hacker Way, Menlo Park", PlaceSuggestion.Type.GENERAL),
        PlaceSuggestion("giants","Oracle Park","24 Willie Mays Plaza, SF", PlaceSuggestion.Type.GENERAL)
    )

    suspend fun getAllPlaces() : List<PlaceSuggestion> {
        delay(300)
        return base
    }

    /**
     * Simulates a network search. Filter by query in primary/secondary.
     */
    suspend fun search(query: String, shouldError: Boolean = false): List<PlaceSuggestion> {
        delay(400) // fake latency
        if (shouldError) error("Network unavailable")
        if (query.isBlank()) return emptyList()
        val q = query.trim().lowercase()
        val filtered = base.filter { it.primary.lowercase().contains(q) || it.secondary.lowercase().contains(q) }
        // Shuffle slightly to avoid deterministic order in practice
        return filtered.shuffled(Random(query.hashCode()))
    }
}
