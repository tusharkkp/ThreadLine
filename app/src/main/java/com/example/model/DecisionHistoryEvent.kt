package com.example.model

enum class EventType {
    ORIGINAL_DECISION,
    DISCUSSION,
    UPDATED_DECISION,
    POTENTIAL_CONFLICT,
    RESOLUTION
}

data class DecisionHistoryEvent(
    val id: String,
    val timestamp: String,
    val type: EventType,
    val speaker: String,
    val quote: String,
    val contextNote: String? = null,
    val value: String? = null
)
