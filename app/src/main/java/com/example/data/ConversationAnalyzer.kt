package com.example.data

import com.example.model.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

object ConversationAnalyzer {

    /**
     * Simulates the multi-step AI pipeline with progress callbacks.
     */
    suspend fun analyzeConversation(
        chatText: String,
        projectName: String = "Imported Hackathon Chat",
        onProgress: (stepName: String, progress: Float) -> Unit
    ): Project {
        onProgress("Reading messages & sanitizing timestamps...", 0.15f)
        delay(350)

        val parsedMessages = WhatsAppChatParser.parseChatText(chatText)

        onProgress("Identifying team decisions & consensus...", 0.35f)
        delay(350)

        onProgress("Connecting related message threads across time...", 0.55f)
        delay(350)

        onProgress("Detecting superseded decisions & changes...", 0.75f)
        delay(350)

        onProgress("Finding unresolved questions & action items...", 0.90f)
        delay(300)

        onProgress("Building project memory...", 1.0f)
        delay(250)

        // If the imported text matches or contains our demo keywords, return the rich SIH project model
        if (chatText.contains("Smart India Hackathon", ignoreCase = true) ||
            chatText.contains("Supabase", ignoreCase = true) ||
            chatText.contains("Tushar", ignoreCase = true)
        ) {
            val base = DemoDataProvider.getSmartIndiaHackathonProject()
            return base.copy(
                name = if (projectName.isNotBlank()) projectName else base.name,
                rawChatContent = chatText,
                messages = if (parsedMessages.isNotEmpty()) parsedMessages else base.messages
            )
        }

        // Otherwise generate a dynamically extracted project from the parsed chat
        return buildDynamicProject(projectName, chatText, parsedMessages)
    }

    private fun buildDynamicProject(name: String, rawChat: String, messages: List<Message>): Project {
        val decisions = mutableListOf<Decision>()
        val actions = mutableListOf<ActionItem>()
        val openQuestions = mutableListOf<OpenQuestion>()
        val changes = mutableListOf<DecisionChange>()
        val conflicts = mutableListOf<Conflict>()
        val activities = mutableListOf<ActivityTimelineItem>()

        var decCounter = 1
        var actCounter = 1
        var qCounter = 1

        for (msg in messages) {
            when (msg.tag) {
                MessageTag.DECISION_ORIGIN, MessageTag.DECISION_UPDATE -> {
                    val decId = "dec_dyn_$decCounter"
                    val dec = Decision(
                        id = decId,
                        category = "Project Decision #$decCounter",
                        title = "Decision on ${msg.text.take(30)}...",
                        currentValue = msg.text.take(60),
                        previousValue = null,
                        status = DecisionStatus.CONFIRMED,
                        decidedBy = msg.sender,
                        createdAt = msg.timestamp,
                        updatedAt = msg.timestamp,
                        participants = listOf(msg.sender),
                        aiInsight = "Decision extracted from message sent by ${msg.sender}.",
                        confidence = "High",
                        historyEvents = listOf(
                            DecisionHistoryEvent(
                                id = "ev_$decCounter",
                                timestamp = msg.timestamp,
                                type = EventType.ORIGINAL_DECISION,
                                speaker = msg.sender,
                                quote = msg.text
                            )
                        )
                    )
                    decisions.add(dec)
                    activities.add(
                        ActivityTimelineItem(
                            id = "act_ev_$decCounter",
                            timestamp = msg.timestamp,
                            title = "Decision logged",
                            description = "${msg.sender}: ${msg.text.take(45)}...",
                            author = msg.sender,
                            type = ActivityType.DECISION_CONFIRMED,
                            referenceId = decId
                        )
                    )
                    decCounter++
                }
                MessageTag.ACTION_ITEM -> {
                    val actId = "act_dyn_$actCounter"
                    actions.add(
                        ActionItem(
                            id = actId,
                            owner = msg.sender,
                            task = msg.text.removePrefix("@").take(80),
                            deadline = "Upcoming",
                            status = ActionStatus.PENDING,
                            sourceQuote = msg.text,
                            timestamp = msg.timestamp,
                            isMyTask = false
                        )
                    )
                    activities.add(
                        ActivityTimelineItem(
                            id = "act_item_ev_$actCounter",
                            timestamp = msg.timestamp,
                            title = "Action item assigned",
                            description = "${msg.sender} → ${msg.text.take(40)}...",
                            author = msg.sender,
                            type = ActivityType.ACTION_ASSIGNED,
                            referenceId = actId
                        )
                    )
                    actCounter++
                }
                MessageTag.OPEN_QUESTION -> {
                    val qId = "q_dyn_$qCounter"
                    openQuestions.add(
                        OpenQuestion(
                            id = qId,
                            question = msg.text,
                            askedBy = msg.sender,
                            timestamp = msg.timestamp,
                            contextQuote = msg.text,
                            isResolved = false
                        )
                    )
                    activities.add(
                        ActivityTimelineItem(
                            id = "q_ev_$qCounter",
                            timestamp = msg.timestamp,
                            title = "Question raised",
                            description = "${msg.sender}: ${msg.text.take(40)}...",
                            author = msg.sender,
                            type = ActivityType.QUESTION_OPENED,
                            referenceId = qId
                        )
                    )
                    qCounter++
                }
                MessageTag.CONFLICT -> {
                    conflicts.add(
                        Conflict(
                            id = "conf_dyn_1",
                            relatedDecisionId = decisions.firstOrNull()?.id ?: "dec_dyn_1",
                            relatedDecisionTitle = "Architecture & Implementation",
                            description = "Contradiction flagged in chat: ${msg.text.take(60)}",
                            quote = msg.text,
                            author = msg.sender,
                            timestamp = msg.timestamp,
                            severity = ConflictSeverity.HIGH,
                            suggestedAction = "Clarify team requirements to prevent duplicated work."
                        )
                    )
                }
                else -> {}
            }
        }

        // Fallback items if chat was minimal
        if (decisions.isEmpty()) {
            decisions.add(
                Decision(
                    id = "dec_dyn_default",
                    category = "General Architecture",
                    title = "Primary Team Workflow",
                    currentValue = "Agile Sprint & Continuous Delivery",
                    status = DecisionStatus.CONFIRMED,
                    decidedBy = "Team",
                    createdAt = "Today",
                    updatedAt = "Today",
                    participants = messages.map { it.sender }.distinct().take(4),
                    aiInsight = "Extracted based on overall team discussion.",
                    historyEvents = emptyList()
                )
            )
        }

        val techStack = decisions.take(4).map {
            TechStackItem(it.category, it.currentValue.take(24), "Confirmed", it.id)
        }

        return Project(
            id = "proj_${System.currentTimeMillis()}",
            name = name.ifBlank { "Analyzed Team Project" },
            lastAnalyzed = "Just now",
            rawChatContent = rawChat,
            messagesCount = messages.size,
            decisionsCount = decisions.size,
            openQuestionsCount = openQuestions.size,
            actionItemsCount = actions.size,
            changedDecisionsCount = changes.size,
            potentialConflictsCount = conflicts.size,
            techStack = techStack,
            decisions = decisions,
            actions = actions,
            openQuestions = openQuestions,
            changes = changes,
            conflicts = conflicts,
            activities = activities,
            messages = messages
        )
    }
}
