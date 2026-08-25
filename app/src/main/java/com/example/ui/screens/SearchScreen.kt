package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Decision
import com.example.model.Project
import com.example.ui.components.DecisionCard
import com.example.ui.components.ActionItemCard
import com.example.ui.components.OpenQuestionCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    project: Project,
    onBack: () -> Unit,
    onDecisionClick: (Decision) -> Unit,
    onActionToggle: (String) -> Unit,
    onQuestionResolve: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredDecisions = remember(searchQuery, project) {
        if (searchQuery.isBlank()) emptyList()
        else project.decisions.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.category.contains(searchQuery, ignoreCase = true) ||
            it.currentValue.contains(searchQuery, ignoreCase = true) ||
            (it.previousValue?.contains(searchQuery, ignoreCase = true) == true) ||
            it.participants.any { p -> p.contains(searchQuery, ignoreCase = true) }
        }
    }

    val filteredActions = remember(searchQuery, project) {
        if (searchQuery.isBlank()) emptyList()
        else project.actions.filter {
            it.task.contains(searchQuery, ignoreCase = true) ||
            it.owner.contains(searchQuery, ignoreCase = true) ||
            it.sourceQuote.contains(searchQuery, ignoreCase = true)
        }
    }

    val filteredQuestions = remember(searchQuery, project) {
        if (searchQuery.isBlank()) emptyList()
        else project.openQuestions.filter {
            it.question.contains(searchQuery, ignoreCase = true) ||
            it.askedBy.contains(searchQuery, ignoreCase = true)
        }
    }

    val filteredMessages = remember(searchQuery, project) {
        if (searchQuery.isBlank()) emptyList()
        else project.messages.filter {
            it.text.contains(searchQuery, ignoreCase = true) ||
            it.sender.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search Firebase, Supabase, Tushar...", fontSize = 14.sp) },
                        singleLine = true,
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .testTag("search_input_field"),
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = PrimaryIndigo,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (searchQuery.isBlank()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Search project memory",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Find decisions, actions, participants, and chat quotes",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                // Decisions Results
                if (filteredDecisions.isNotEmpty()) {
                    item {
                        Text(
                            text = "Decisions (${filteredDecisions.size})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryIndigo
                        )
                    }
                    items(filteredDecisions) { dec ->
                        DecisionCard(decision = dec, onClick = { onDecisionClick(dec) })
                    }
                }

                // Actions Results
                if (filteredActions.isNotEmpty()) {
                    item {
                        Text(
                            text = "Action Items (${filteredActions.size})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentEmerald
                        )
                    }
                    items(filteredActions) { act ->
                        ActionItemCard(action = act, onToggle = { onActionToggle(act.id) })
                    }
                }

                // Open Questions Results
                if (filteredQuestions.isNotEmpty()) {
                    item {
                        Text(
                            text = "Questions (${filteredQuestions.size})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentAmber
                        )
                    }
                    items(filteredQuestions) { q ->
                        OpenQuestionCard(question = q, onResolve = { onQuestionResolve(q.id) })
                    }
                }

                // Chat Messages Results
                if (filteredMessages.isNotEmpty()) {
                    item {
                        Text(
                            text = "Chat Messages (${filteredMessages.size})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    items(filteredMessages) { msg ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = msg.sender,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = msg.timestamp,
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = msg.text,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                if (filteredDecisions.isEmpty() && filteredActions.isEmpty() && filteredQuestions.isEmpty() && filteredMessages.isEmpty()) {
                    item {
                        Text(
                            text = "No results found for \"$searchQuery\"",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 24.dp)
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(28.dp)) }
        }
    }
}
