package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ChatQaMessage(
    val id: String,
    val query: String,
    val answer: String,
    val timestamp: String,
    val relatedDecisionId: String? = null
)

class ProjectRepository {

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    private val _activeProject = MutableStateFlow<Project?>(null)
    val activeProject: StateFlow<Project?> = _activeProject.asStateFlow()

    private val _qaHistory = MutableStateFlow<List<ChatQaMessage>>(emptyList())
    val qaHistory: StateFlow<List<ChatQaMessage>> = _qaHistory.asStateFlow()

    init {
        // Initialize with default demo project ready for instant showcase
        val sih = DemoDataProvider.getSmartIndiaHackathonProject()
        val mini = DemoDataProvider.getSecondaryProject()
        _projects.value = listOf(sih, mini)
        _activeProject.value = sih
        
        // Initial seed Q&A item
        _qaHistory.value = listOf(
            ChatQaMessage(
                id = "qa_1",
                query = "What is our current backend?",
                answer = """Your current backend decision is Supabase.

The team initially chose Firebase at 10:32 AM, but changed the decision to Supabase at 4:18 PM because the team preferred PostgreSQL support.

One potential conflict remains: Firebase authentication may already have been implemented by Tejas at 5:02 PM.""",
                timestamp = "Just now",
                relatedDecisionId = "dec_backend"
            )
        )
    }

    fun selectProject(projectId: String) {
        val proj = _projects.value.find { it.id == projectId }
        if (proj != null) {
            _activeProject.value = proj
        }
    }

    fun addOrUpdateProject(project: Project) {
        _projects.update { currentList ->
            val index = currentList.indexOfFirst { it.id == project.id }
            if (index >= 0) {
                currentList.toMutableList().apply { set(index, project) }
            } else {
                listOf(project) + currentList
            }
        }
        _activeProject.value = project
    }

    fun deleteProject(projectId: String) {
        _projects.update { currentList ->
            currentList.filter { it.id != projectId }
        }
        if (_activeProject.value?.id == projectId) {
            _activeProject.value = _projects.value.firstOrNull()
        }
    }

    fun toggleActionItem(actionId: String) {
        _activeProject.update { currentProj ->
            if (currentProj == null) return@update null
            val updatedActions = currentProj.actions.map { action ->
                if (action.id == actionId) {
                    val nextStatus = if (action.status == ActionStatus.COMPLETED) ActionStatus.IN_PROGRESS else ActionStatus.COMPLETED
                    action.copy(status = nextStatus)
                } else action
            }
            val updated = currentProj.copy(actions = updatedActions)
            updateProjectInList(updated)
            updated
        }
    }

    fun resolveConflict(conflictId: String) {
        _activeProject.update { currentProj ->
            if (currentProj == null) return@update null
            val updatedConflicts = currentProj.conflicts.map { conf ->
                if (conf.id == conflictId) conf.copy(isResolved = true) else conf
            }
            val updatedDecisions = currentProj.decisions.map { dec ->
                if (dec.id == "dec_backend") dec.copy(isResolved = true, status = DecisionStatus.CONFIRMED) else dec
            }
            val updated = currentProj.copy(
                conflicts = updatedConflicts,
                decisions = updatedDecisions,
                potentialConflictsCount = updatedConflicts.count { !it.isResolved }
            )
            updateProjectInList(updated)
            updated
        }
    }

    fun resolveQuestion(questionId: String, note: String = "Resolved by team") {
        _activeProject.update { currentProj ->
            if (currentProj == null) return@update null
            val updatedQuestions = currentProj.openQuestions.map { q ->
                if (q.id == questionId) q.copy(isResolved = true, resolutionNote = note) else q
            }
            val updated = currentProj.copy(
                openQuestions = updatedQuestions,
                openQuestionsCount = updatedQuestions.count { !it.isResolved }
            )
            updateProjectInList(updated)
            updated
        }
    }

    fun askQuestion(query: String): String {
        val proj = _activeProject.value ?: DemoDataProvider.getSmartIndiaHackathonProject()
        val answer = DemoDataProvider.answerProjectQuestion(query, proj)
        val relatedId = if (query.contains("backend", ignoreCase = true) || query.contains("supabase", ignoreCase = true) || query.contains("firebase", ignoreCase = true)) {
            "dec_backend"
        } else if (query.contains("frontend", ignoreCase = true) || query.contains("compose", ignoreCase = true)) {
            "dec_frontend"
        } else null

        val qaMsg = ChatQaMessage(
            id = "qa_${System.currentTimeMillis()}",
            query = query,
            answer = answer,
            timestamp = "Just now",
            relatedDecisionId = relatedId
        )
        _qaHistory.update { listOf(qaMsg) + it }
        return answer
    }

    private fun updateProjectInList(project: Project) {
        _projects.update { list ->
            list.map { if (it.id == project.id) project else it }
        }
    }

    companion object {
        val instance = ProjectRepository()
    }
}
