package com.example.model

data class OpenQuestion(
    val id: String,
    val question: String,
    val askedBy: String,
    val timestamp: String,
    val contextQuote: String,
    val isResolved: Boolean = false,
    val resolutionNote: String? = null
)
