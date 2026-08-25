package com.example.model

enum class ActionStatus {
    IN_PROGRESS,
    PENDING,
    COMPLETED
}

data class ActionItem(
    val id: String,
    val owner: String,
    val task: String,
    val deadline: String? = null,
    val status: ActionStatus = ActionStatus.PENDING,
    val sourceQuote: String,
    val timestamp: String,
    val isMyTask: Boolean = false
)
