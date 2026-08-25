package com.example.model

/**
 * Represents a raw or parsed message from an exported conversation.
 */
data class Message(
    val id: String,
    val sender: String,
    val text: String,
    val timestamp: String,
    val rawTimestamp: Long = 0L,
    val isSystem: Boolean = false,
    val tag: MessageTag? = null
)

enum class MessageTag {
    DECISION_ORIGIN,
    DECISION_UPDATE,
    DISCUSSION,
    CONFLICT,
    ACTION_ITEM,
    OPEN_QUESTION
}
