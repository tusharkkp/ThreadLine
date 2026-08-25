package com.example.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.DemoDataProvider
import com.example.data.ProjectRepository
import com.example.model.Decision
import com.example.ui.screens.*
import com.example.ui.theme.*

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Import : Screen("import")
    object Analysis : Screen("analysis/{projectName}") {
        fun createRoute(projectName: String) = "analysis/$projectName"
    }
    object Dashboard : Screen("dashboard")
    object DecisionDetail : Screen("decision_detail/{decisionId}") {
        fun createRoute(decisionId: String) = "decision_detail/$decisionId"
    }
    object AskThreadline : Screen("ask_threadline")
    object Search : Screen("search")
    object ChatLog : Screen("chat_log")
    object ProjectSettings : Screen("settings")
}

@Composable
fun ThreadlineApp(
    repository: ProjectRepository = remember { ProjectRepository.instance }
) {
    val navController = rememberNavController()
    val projects by repository.projects.collectAsStateWithLifecycle()
    val activeProject by repository.activeProject.collectAsStateWithLifecycle()
    val qaHistory by repository.qaHistory.collectAsStateWithLifecycle()

    var activeTab by remember { mutableStateOf(DashboardTab.OVERVIEW) }
    var pendingImportText by remember { mutableStateOf("") }
    var pendingProjectName by remember { mutableStateOf("") }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomNav = currentRoute in listOf(
        Screen.Dashboard.route,
        Screen.AskThreadline.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomNav && activeProject != null) {
                NavigationBar(
                    containerColor = LightSurfaceAlt,
                    tonalElevation = 0.dp
                ) {
                    val navItemColors = NavigationBarItemDefaults.colors(
                        selectedIconColor = OnPrimaryContainer,
                        selectedTextColor = OnPrimaryContainer,
                        indicatorColor = PrimaryPillActive,
                        unselectedIconColor = LightTextSecondary,
                        unselectedTextColor = LightTextSecondary
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        selected = currentRoute == Screen.Home.route,
                        onClick = { navController.navigate(Screen.Home.route) },
                        colors = navItemColors,
                        modifier = Modifier.testTag("nav_home")
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                        label = { Text("Overview", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        selected = currentRoute == Screen.Dashboard.route && activeTab == DashboardTab.OVERVIEW,
                        onClick = {
                            activeTab = DashboardTab.OVERVIEW
                            if (currentRoute != Screen.Dashboard.route) {
                                navController.navigate(Screen.Dashboard.route)
                            }
                        },
                        colors = navItemColors,
                        modifier = Modifier.testTag("nav_dashboard")
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Timeline, contentDescription = "Timeline") },
                        label = { Text("Timeline", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        selected = currentRoute == Screen.Dashboard.route && (activeTab == DashboardTab.DECISIONS || activeTab == DashboardTab.CHANGES),
                        onClick = {
                            activeTab = DashboardTab.DECISIONS
                            if (currentRoute != Screen.Dashboard.route) {
                                navController.navigate(Screen.Dashboard.route)
                            }
                        },
                        colors = navItemColors,
                        modifier = Modifier.testTag("nav_decisions")
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Checklist, contentDescription = "Tasks") },
                        label = { Text("Tasks", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        selected = currentRoute == Screen.Dashboard.route && activeTab == DashboardTab.ACTIONS,
                        onClick = {
                            activeTab = DashboardTab.ACTIONS
                            if (currentRoute != Screen.Dashboard.route) {
                                navController.navigate(Screen.Dashboard.route)
                            }
                        },
                        colors = navItemColors,
                        modifier = Modifier.testTag("nav_actions")
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "Ask") },
                        label = { Text("Ask", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        selected = currentRoute == Screen.AskThreadline.route,
                        onClick = {
                            if (currentRoute != Screen.AskThreadline.route) {
                                navController.navigate(Screen.AskThreadline.route)
                            }
                        },
                        colors = navItemColors,
                        modifier = Modifier.testTag("nav_ask_ai")
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    onGetStarted = {
                        navController.navigate(Screen.Home.route)
                    },
                    onLoadDemo = {
                        val sih = DemoDataProvider.getSmartIndiaHackathonProject()
                        repository.addOrUpdateProject(sih)
                        navController.navigate(Screen.Dashboard.route)
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    projects = projects,
                    onSelectProject = { projectId ->
                        repository.selectProject(projectId)
                        activeTab = DashboardTab.OVERVIEW
                        navController.navigate(Screen.Dashboard.route)
                    },
                    onImportClick = {
                        navController.navigate(Screen.Import.route)
                    },
                    onTryDemoClick = {
                        val sih = DemoDataProvider.getSmartIndiaHackathonProject()
                        repository.addOrUpdateProject(sih)
                        activeTab = DashboardTab.OVERVIEW
                        navController.navigate(Screen.Dashboard.route)
                    }
                )
            }

            composable(Screen.Import.route) {
                ImportScreen(
                    onBack = { navController.popBackStack() },
                    onStartAnalysis = { rawText, projName ->
                        pendingImportText = rawText
                        pendingProjectName = projName
                        navController.navigate(Screen.Analysis.createRoute(projName))
                    },
                    onTryDemo = {
                        pendingImportText = DemoDataProvider.DEMO_WHATSAPP_RAW_TEXT
                        pendingProjectName = "Smart India Hackathon"
                        navController.navigate(Screen.Analysis.createRoute("Smart India Hackathon"))
                    }
                )
            }

            composable(Screen.Analysis.route) {
                AnalysisScreen(
                    rawText = pendingImportText,
                    projectName = pendingProjectName,
                    onAnalysisComplete = { analyzedProject ->
                        repository.addOrUpdateProject(analyzedProject)
                        activeTab = DashboardTab.OVERVIEW
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Home.route)
                        }
                    }
                )
            }

            composable(Screen.Dashboard.route) {
                val currentProject = activeProject ?: DemoDataProvider.getSmartIndiaHackathonProject()
                DashboardScreen(
                    project = currentProject,
                    selectedTab = activeTab,
                    onTabSelected = { activeTab = it },
                    onDecisionClick = { decision ->
                        navController.navigate(Screen.DecisionDetail.createRoute(decision.id))
                    },
                    onActionToggle = { actionId ->
                        repository.toggleActionItem(actionId)
                    },
                    onQuestionResolve = { qId ->
                        repository.resolveQuestion(qId)
                    },
                    onConflictResolve = { confId ->
                        repository.resolveConflict(confId)
                    },
                    onSearchClick = {
                        navController.navigate(Screen.Search.route)
                    },
                    onChatLogClick = {
                        navController.navigate(Screen.ChatLog.route)
                    },
                    onSettingsClick = {
                        navController.navigate(Screen.ProjectSettings.route)
                    }
                )
            }

            composable(Screen.DecisionDetail.route) { backStackEntry ->
                val decisionId = backStackEntry.arguments?.getString("decisionId")
                val currentProj = activeProject ?: DemoDataProvider.getSmartIndiaHackathonProject()
                val decision = currentProj.decisions.find { it.id == decisionId }
                    ?: currentProj.decisions.first()

                DecisionDetailScreen(
                    decision = decision,
                    onBack = { navController.popBackStack() },
                    onResolveConflict = {
                        val conf = currentProj.conflicts.find { it.relatedDecisionId == decision.id }
                        if (conf != null) repository.resolveConflict(conf.id)
                    },
                    onAskAboutDecision = { topic ->
                        repository.askQuestion("Tell me about the decision history for $topic")
                        navController.navigate(Screen.AskThreadline.route)
                    },
                    onViewChatContext = {
                        navController.navigate(Screen.ChatLog.route)
                    }
                )
            }

            composable(Screen.AskThreadline.route) {
                val currentProj = activeProject ?: DemoDataProvider.getSmartIndiaHackathonProject()
                AskThreadlineScreen(
                    project = currentProj,
                    qaHistory = qaHistory,
                    onAskQuery = { query ->
                        repository.askQuestion(query)
                    },
                    onViewDecision = { dec ->
                        navController.navigate(Screen.DecisionDetail.createRoute(dec.id))
                    }
                )
            }

            composable(Screen.Search.route) {
                val currentProj = activeProject ?: DemoDataProvider.getSmartIndiaHackathonProject()
                SearchScreen(
                    project = currentProj,
                    onBack = { navController.popBackStack() },
                    onDecisionClick = { decision ->
                        navController.navigate(Screen.DecisionDetail.createRoute(decision.id))
                    },
                    onActionToggle = { actionId ->
                        repository.toggleActionItem(actionId)
                    },
                    onQuestionResolve = { qId ->
                        repository.resolveQuestion(qId)
                    }
                )
            }

            composable(Screen.ChatLog.route) {
                val currentProj = activeProject ?: DemoDataProvider.getSmartIndiaHackathonProject()
                ChatLogScreen(
                    project = currentProj,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.ProjectSettings.route) {
                val currentProj = activeProject ?: DemoDataProvider.getSmartIndiaHackathonProject()
                ProjectSettingsScreen(
                    project = currentProj,
                    onBack = { navController.popBackStack() },
                    onReAnalyze = {
                        pendingImportText = currentProj.rawChatContent
                        pendingProjectName = currentProj.name
                        navController.navigate(Screen.Analysis.createRoute(currentProj.name))
                    },
                    onDeleteProject = {
                        repository.deleteProject(currentProj.id)
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
