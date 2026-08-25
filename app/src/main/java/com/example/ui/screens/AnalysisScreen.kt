package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ConversationAnalyzer
import com.example.data.DemoDataProvider
import com.example.model.Project
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun AnalysisScreen(
    rawText: String,
    projectName: String,
    onAnalysisComplete: (Project) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentStepName by remember { mutableStateOf("Reading messages...") }
    var progress by remember { mutableFloatStateOf(0.1f) }
    var step1Done by remember { mutableStateOf(false) }
    var step2Done by remember { mutableStateOf(false) }
    var step3Done by remember { mutableStateOf(false) }
    var step4Done by remember { mutableStateOf(false) }
    var step5Done by remember { mutableStateOf(false) }
    var step6Done by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse_ai")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    LaunchedEffect(Unit) {
        val project = ConversationAnalyzer.analyzeConversation(
            chatText = rawText.ifBlank { DemoDataProvider.DEMO_WHATSAPP_RAW_TEXT },
            projectName = projectName.ifBlank { "Smart India Hackathon" }
        ) { stepName, p ->
            currentStepName = stepName
            progress = p
            if (p >= 0.15f) step1Done = true
            if (p >= 0.35f) step2Done = true
            if (p >= 0.55f) step3Done = true
            if (p >= 0.75f) step4Done = true
            if (p >= 0.90f) step5Done = true
            if (p >= 1.0f) step6Done = true
        }

        delay(400)
        onAnalysisComplete(project)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkBackground,
                        Color(0xFF0F172A),
                        Color(0xFF1E1B4B)
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Pulse AI Node
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .scale(pulseScale)
                    .clip(CircleShape)
                    .background(PrimaryIndigo.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(PrimaryIndigo, AccentCyan)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Analyzing",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Understanding your conversation...",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = currentStepName,
                fontSize = 13.sp,
                color = AccentCyanLight,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Progress Bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .testTag("analysis_progress_bar"),
                color = PrimaryIndigoLight,
                trackColor = DarkSurfaceVariant
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Step Checklist Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = DarkSurface.copy(alpha = 0.85f),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CheckStepRow(text = "Reading messages & timestamps", isDone = step1Done)
                    CheckStepRow(text = "Identifying decisions & consensus", isDone = step2Done)
                    CheckStepRow(text = "Connecting related message threads", isDone = step3Done)
                    CheckStepRow(text = "Detecting superseded decisions & changes", isDone = step4Done)
                    CheckStepRow(text = "Finding unresolved questions & action items", isDone = step5Done)
                    CheckStepRow(text = "Building living project memory", isDone = step6Done)
                }
            }
        }
    }
}

@Composable
private fun CheckStepRow(text: String, isDone: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (isDone) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Done",
                tint = AccentEmerald,
                modifier = Modifier.size(18.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.RadioButtonUnchecked,
                contentDescription = "Pending",
                tint = DarkTextMuted,
                modifier = Modifier.size(18.dp)
            )
        }

        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = if (isDone) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isDone) DarkTextPrimary else DarkTextMuted
        )
    }
}
