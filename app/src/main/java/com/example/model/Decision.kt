package com.example.model

enum class DecisionStatus {
    CONFIRMED,
    SUPERSEDED, // or CHANGED
    UNDER_DISCUSSION,
    CONFLICT_FLAGGED
}

data class Decision(
    val id: String,
    val category: String, // e.g. "Backend platform", "UI framework", "AI model strategy", "Database", etc.
    val title: String,
    val currentValue: String,
    val previousValue: String? = null,
    val status: DecisionStatus,
    val decidedBy: String,
    val createdAt: String,
    val updatedAt: String,
    val participants: List<String>,
    val historyEvents: List<DecisionHistoryEvent>,
    val aiInsight: String,
    val confidence: String = "High", // "High", "Medium"
    val isResolved: Boolean = true
)
