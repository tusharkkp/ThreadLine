package com.example.model

enum class ConflictSeverity {
    HIGH,
    MEDIUM,
    LOW
}

data class Conflict(
    val id: String,
    val relatedDecisionId: String,
    val relatedDecisionTitle: String,
    val description: String,
    val quote: String,
    val author: String,
    val timestamp: String,
    val severity: ConflictSeverity = ConflictSeverity.HIGH,
    val suggestedAction: String,
    val isResolved: Boolean = false
)
