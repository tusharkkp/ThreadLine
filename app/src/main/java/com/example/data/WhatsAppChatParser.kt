package com.example.data

import com.example.model.Message
import com.example.model.MessageTag

object WhatsAppChatParser {

    /**
     * Parses WhatsApp exported text content into a structured list of Messages.
     * Supports:
     * - 24/08/24, 10:12 AM - Sender: Message text
     * - 24/08/2024, 10:12 - Sender: Message text
     * - [24/08/24, 10:12:05] Sender: Message text
     * - Multi-line messages
     */
    fun parseChatText(rawText: String): List<Message> {
        val lines = rawText.lines()
        val messages = mutableListOf<Message>()
        var idCounter = 1

        val pattern1 = Regex("""^(\d{1,2}/\d{1,2}/\d{2,4},\s+\d{1,2}:\d{2}(?::\d{2})?\s*(?:AM|PM|am|pm)?)\s*-\s*([^:]+):\s*(.*)$""")
        val pattern2 = Regex("""^\[(\d{1,2}/\d{1,2}/\d{2,4},\s+\d{1,2}:\d{2}(?::\d{2})?\s*(?:AM|PM|am|pm)?)\]\s*([^:]+):\s*(.*)$""")

        var currentSender = ""
        var currentTimestamp = ""
        var currentText = StringBuilder()

        fun flushCurrent() {
            if (currentSender.isNotEmpty() && currentText.isNotEmpty()) {
                val text = currentText.toString().trim()
                val tag = detectTag(text)
                messages.add(
                    Message(
                        id = "msg_${idCounter++}",
                        sender = currentSender,
                        text = text,
                        timestamp = currentTimestamp,
                        tag = tag
                    )
                )
                currentText.clear()
            }
        }

        for (line in lines) {
            val trimmed = line.trim()
            if (trimmed.isEmpty()) continue

            // System messages (e.g. Encryption notice)
            if (trimmed.contains("end-to-end encrypted", ignoreCase = true) ||
                trimmed.contains("created group", ignoreCase = true) ||
                trimmed.contains("added you", ignoreCase = true)
            ) {
                flushCurrent()
                continue
            }

            val match1 = pattern1.find(trimmed)
            val match2 = if (match1 == null) pattern2.find(trimmed) else null

            val match = match1 ?: match2
            if (match != null) {
                flushCurrent()
                val (timeStr, sender, msgText) = match.destructured
                currentTimestamp = timeStr.substringAfter(", ").trim()
                currentSender = sender.trim()
                currentText.append(msgText)
            } else {
                if (currentSender.isNotEmpty()) {
                    currentText.append("\n").append(trimmed)
                }
            }
        }
        flushCurrent()

        return messages
    }

    private fun detectTag(text: String): MessageTag {
        val lower = text.lowercase()
        return when {
            lower.contains("conflict") || lower.contains("already finished") || lower.contains("already built") -> MessageTag.CONFLICT
            lower.contains("action:") || lower.contains("i will") || lower.contains("i'll") || lower.contains("assigned to") -> MessageTag.ACTION_ITEM
            lower.contains("final:") || lower.contains("confirmed:") || lower.contains("switched to") || lower.contains("decided:") -> MessageTag.DECISION_UPDATE
            lower.contains("let's go with") || lower.contains("karu kya") || lower.contains("let's use") -> MessageTag.DECISION_ORIGIN
            lower.contains("?") -> MessageTag.OPEN_QUESTION
            else -> MessageTag.DISCUSSION
        }
    }
}
