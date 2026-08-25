package com.example.model

data class TechStackItem(
    val category: String, // "Backend", "Frontend", "AI Strategy", "Database", "Auth"
    val chosenTechnology: String,
    val status: String, // "Confirmed", "Under discussion", "Conflict flagged"
    val decisionId: String
)

data class ActivityTimelineItem(
    val id: String,
    val timestamp: String,
    val title: String,
    val description: String,
    val author: String,
    val type: ActivityType,
    val referenceId: String? = null
)

enum class ActivityType {
    DECISION_CHANGED,
    DECISION_CONFIRMED,
    ACTION_ASSIGNED,
    QUESTION_OPENED,
    CONFLICT_DETECTED
}

data class Project(
    val id: String,
    val name: String,
    val lastAnalyzed: String,
    val rawChatContent: String,
    val messagesCount: Int,
    val decisionsCount: Int,
    val openQuestionsCount: Int,
    val actionItemsCount: Int,
    val changedDecisionsCount: Int,
    val potentialConflictsCount: Int,
    val techStack: List<TechStackItem>,
    val decisions: List<Decision>,
    val actions: List<ActionItem>,
    val openQuestions: List<OpenQuestion>,
    val changes: List<DecisionChange>,
    val conflicts: List<Conflict>,
    val activities: List<ActivityTimelineItem>,
    val messages: List<Message>
)
