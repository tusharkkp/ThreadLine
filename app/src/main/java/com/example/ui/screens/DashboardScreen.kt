package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
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
import com.example.data.DemoDataProvider
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*

enum class DashboardTab(val title: String) {
    OVERVIEW("Overview"),
    DECISIONS("Decisions"),
    ACTIONS("Actions"),
    OPEN("Open"),
    CHANGES("Changes")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    project: Project,
    selectedTab: DashboardTab,
    onTabSelected: (DashboardTab) -> Unit,
    onDecisionClick: (Decision) -> Unit,
    onActionToggle: (String) -> Unit,
    onQuestionResolve: (String) -> Unit,
    onConflictResolve: (String) -> Unit,
    onSearchClick: () -> Unit,
    onChatLogClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                // Top Brand & Action Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "THREADLINE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryTeal,
                        letterSpacing = 2.sp
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onSearchClick,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(LightSurfaceVariant)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search project memory",
                                tint = LightTextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        IconButton(
                            onClick = onChatLogClick,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(LightSurfaceVariant)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Chat,
                                contentDescription = "View chat log",
                                tint = LightTextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        IconButton(
                            onClick = onSettingsClick,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(LightSurfaceVariant)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Project Settings",
                                tint = LightTextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(PrimaryTeal),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = project.name.firstOrNull()?.uppercase() ?: "T",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Project Title & Subtitle with green live dot
                Text(
                    text = project.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = (-0.5).sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF22C55E))
                    )
                    Text(
                        text = "Updated ${project.lastAnalyzed}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 3-Metric Summary Grid (Decisions / Changes / Conflicts)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Decisions Stat Card
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = PrimaryContainer,
                        modifier = Modifier
                            .weight(1f)
                            .height(84.dp)
                            .clickable { onTabSelected(DashboardTab.DECISIONS) }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "DECISIONS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnPrimaryContainer.copy(alpha = 0.7f),
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "${project.decisionsCount.toString().padStart(2, '0')}",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnPrimaryContainer
                            )
                        }
                    }

                    // Changes Stat Card
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = WarmAccentContainer,
                        modifier = Modifier
                            .weight(1f)
                            .height(84.dp)
                            .clickable { onTabSelected(DashboardTab.CHANGES) }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "CHANGES",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmAccentContainer.copy(alpha = 0.7f),
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "${project.changedDecisionsCount.toString().padStart(2, '0')}",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnWarmAccentContainer
                            )
                        }
                    }

                    // Conflicts Stat Card
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = ConflictContainer,
                        border = BorderStroke(1.dp, ConflictBorder),
                        modifier = Modifier
                            .weight(1f)
                            .height(84.dp)
                            .clickable { onTabSelected(DashboardTab.CHANGES) }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "CONFLICTS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnConflictContainer.copy(alpha = 0.7f),
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "${project.potentialConflictsCount.toString().padStart(2, '0')}",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = ConflictRed
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Sleek Tab Row
            ScrollableTabRow(
                selectedTabIndex = selectedTab.ordinal,
                edgePadding = 20.dp,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = PrimaryTeal,
                divider = {
                    HorizontalDivider(color = LightSurfaceBorder)
                }
            ) {
                DashboardTab.values().forEach { tab ->
                    val badgeCount = when (tab) {
                        DashboardTab.ACTIONS -> project.actionItemsCount
                        DashboardTab.OPEN -> project.openQuestionsCount
                        else -> null
                    }

                    Tab(
                        selected = selectedTab == tab,
                        onClick = { onTabSelected(tab) },
                        modifier = Modifier.testTag("tab_${tab.name.lowercase()}"),
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                Text(
                                    text = tab.title,
                                    fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 13.sp,
                                    color = if (selectedTab == tab) PrimaryTeal else LightTextSecondary
                                )
                                if (badgeCount != null && badgeCount > 0) {
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (tab == DashboardTab.OPEN) LightSurfaceVariant else PrimaryContainer
                                    ) {
                                        Text(
                                            text = if (tab == DashboardTab.OPEN) "$badgeCount Open" else "$badgeCount",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (tab == DashboardTab.OPEN) LightTextSecondary else OnPrimaryContainer,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }

            // Tab Content
            when (selectedTab) {
                DashboardTab.OVERVIEW -> OverviewTabView(
                    project = project,
                    onDecisionClick = onDecisionClick,
                    onConflictResolve = onConflictResolve,
                    onNavigateTab = onTabSelected
                )
                DashboardTab.DECISIONS -> DecisionsTabView(
                    decisions = project.decisions,
                    onDecisionClick = onDecisionClick
                )
                DashboardTab.ACTIONS -> ActionsTabView(
                    actions = project.actions,
                    onToggle = onActionToggle
                )
                DashboardTab.OPEN -> OpenQuestionsTabView(
                    questions = project.openQuestions,
                    onResolve = onQuestionResolve
                )
                DashboardTab.CHANGES -> ChangesTabView(
                    changes = project.changes,
                    conflicts = project.conflicts,
                    decisions = project.decisions,
                    onDecisionClick = onDecisionClick,
                    onConflictResolve = onConflictResolve
                )
            }
        }
    }
}

@Composable
private fun OverviewTabView(
    project: Project,
    onDecisionClick: (Decision) -> Unit,
    onConflictResolve: (String) -> Unit,
    onNavigateTab: (DashboardTab) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Top Summary Card: CURRENT PROJECT STATE
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.35f)),
                tonalElevation = 3.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "CURRENT PROJECT STATE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProjectStateMetricItem(
                            count = "${project.decisionsCount}",
                            label = "Decisions",
                            color = PrimaryIndigo,
                            onClick = { onNavigateTab(DashboardTab.DECISIONS) }
                        )
                        ProjectStateMetricItem(
                            count = "${project.openQuestionsCount}",
                            label = "Open",
                            color = AccentAmber,
                            onClick = { onNavigateTab(DashboardTab.OPEN) }
                        )
                        ProjectStateMetricItem(
                            count = "${project.actionItemsCount}",
                            label = "Actions",
                            color = AccentEmerald,
                            onClick = { onNavigateTab(DashboardTab.ACTIONS) }
                        )
                        ProjectStateMetricItem(
                            count = "${project.changedDecisionsCount}",
                            label = "Changed",
                            color = PrimaryIndigoLight,
                            onClick = { onNavigateTab(DashboardTab.CHANGES) }
                        )
                    }

                    if (project.potentialConflictsCount > 0) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(AccentRose.copy(alpha = 0.12f))
                                .clickable { onNavigateTab(DashboardTab.CHANGES) }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = AccentRose, modifier = Modifier.size(14.dp))
                            Text(
                                text = "${project.potentialConflictsCount} potential conflict needs attention",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentRose
                            )
                        }
                    }
                }
            }
        }

        // Potential Conflicts Alert
        if (project.conflicts.any { !it.isResolved }) {
            item {
                val conflict = project.conflicts.first { !it.isResolved }
                ConflictAlertCard(
                    conflict = conflict,
                    onResolveClick = { onConflictResolve(conflict.id) }
                )
            }
        }

        // Tech Stack State Card
        item {
            TechStackSummaryCard(
                techStack = project.techStack,
                onItemClick = { decId ->
                    val target = project.decisions.find { it.id == decId }
                    if (target != null) onDecisionClick(target)
                }
            )
        }

        // Latest Activity Section Header
        item {
            Text(
                text = "Latest Activity",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Activity Feed Items
        items(project.activities) { activity ->
            ActivityFeedItem(activity = activity)
        }

        item { Spacer(modifier = Modifier.height(28.dp)) }
    }
}

@Composable
private fun ProjectStateMetricItem(
    count: String,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = count,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            color = color
        )
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ActivityFeedItem(activity: ActivityTimelineItem) {
    val (icon, color) = when (activity.type) {
        ActivityType.DECISION_CHANGED -> Pair(Icons.Default.SwapHoriz, WarmAccent)
        ActivityType.DECISION_CONFIRMED -> Pair(Icons.Default.CheckCircle, PrimaryTeal)
        ActivityType.ACTION_ASSIGNED -> Pair(Icons.Default.Assignment, PrimaryTealLight)
        ActivityType.QUESTION_OPENED -> Pair(Icons.Default.HelpOutline, AccentAmber)
        ActivityType.CONFLICT_DETECTED -> Pair(Icons.Default.Warning, ConflictRed)
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, LightSurfaceBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = activity.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = activity.timestamp,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = activity.description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun DecisionsTabView(
    decisions: List<Decision>,
    onDecisionClick: (Decision) -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filtered = when (selectedFilter) {
        "Changed" -> decisions.filter { it.status == DecisionStatus.SUPERSEDED }
        "Confirmed" -> decisions.filter { it.status == DecisionStatus.CONFIRMED }
        else -> decisions
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Filter chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Changed", "Confirmed").forEach { label ->
                    FilterChip(
                        selected = selectedFilter == label,
                        onClick = { selectedFilter = label },
                        label = { Text(label, fontSize = 12.sp) }
                    )
                }
            }
        }

        items(filtered) { decision ->
            DecisionCard(
                decision = decision,
                onClick = { onDecisionClick(decision) }
            )
        }

        item { Spacer(modifier = Modifier.height(28.dp)) }
    }
}

@Composable
private fun ActionsTabView(
    actions: List<ActionItem>,
    onToggle: (String) -> Unit
) {
    var selectedGroup by remember { mutableStateOf("All") }
    val filtered = when (selectedGroup) {
        "My tasks" -> actions.filter { it.isMyTask }
        "Completed" -> actions.filter { it.status == ActionStatus.COMPLETED }
        "Pending" -> actions.filter { it.status != ActionStatus.COMPLETED }
        else -> actions
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "My tasks", "Pending", "Completed").forEach { label ->
                    FilterChip(
                        selected = selectedGroup == label,
                        onClick = { selectedGroup = label },
                        label = { Text(label, fontSize = 12.sp) }
                    )
                }
            }
        }

        items(filtered) { action ->
            ActionItemCard(action = action, onToggle = { onToggle(action.id) })
        }

        item { Spacer(modifier = Modifier.height(28.dp)) }
    }
}

@Composable
private fun OpenQuestionsTabView(
    questions: List<OpenQuestion>,
    onResolve: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${questions.count { !it.isResolved }} unresolved questions needing team consensus",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(questions) { q ->
            OpenQuestionCard(question = q, onResolve = { onResolve(q.id) })
        }

        item { Spacer(modifier = Modifier.height(28.dp)) }
    }
}

@Composable
private fun ChangesTabView(
    changes: List<DecisionChange>,
    conflicts: List<Conflict>,
    decisions: List<Decision>,
    onDecisionClick: (Decision) -> Unit,
    onConflictResolve: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "What changed?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Decisions that evolved or were superseded during conversation",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (conflicts.any { !it.isResolved }) {
            items(conflicts.filter { !it.isResolved }) { conf ->
                ConflictAlertCard(
                    conflict = conf,
                    onResolveClick = { onConflictResolve(conf.id) }
                )
            }
        }

        items(changes) { change ->
            ChangeCard(
                change = change,
                onClick = {
                    val target = decisions.find { it.id == change.decisionId }
                        ?: decisions.firstOrNull()
                    if (target != null) onDecisionClick(target)
                }
            )
        }

        item { Spacer(modifier = Modifier.height(28.dp)) }
    }
}
