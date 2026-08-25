package com.example.model

data class DecisionChange(
    val id: String,
    val decisionId: String,
    val title: String,
    val originalValue: String,
    val newValue: String,
    val originalTimestamp: String,
    val updatedTimestamp: String,
    val reason: String,
    val decidedBy: String,
    val confidence: String = "High",
    val hasConflict: Boolean = false,
    val conflictDescription: String? = null
)
