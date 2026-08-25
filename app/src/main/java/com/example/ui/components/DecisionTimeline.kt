package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DecisionHistoryEvent
import com.example.model.EventType
import com.example.ui.theme.*

@Composable
fun DecisionTimeline(
    events: List<DecisionHistoryEvent>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        events.forEachIndexed { index, event ->
            val isLast = index == events.size - 1
            TimelineEventNode(
                event = event,
                isLast = isLast
            )
        }
    }
}

@Composable
private fun TimelineEventNode(
    event: DecisionHistoryEvent,
    isLast: Boolean
) {
    val (nodeColor, icon, badgeLabel) = when (event.type) {
        EventType.ORIGINAL_DECISION -> Triple(PrimaryTeal, Icons.Default.ChatBubbleOutline, "Original decision")
        EventType.DISCUSSION -> Triple(PrimaryTealLight, Icons.Default.ChatBubbleOutline, "Discussion")
        EventType.UPDATED_DECISION -> Triple(WarmAccent, Icons.Default.SwapHoriz, "Updated decision")
        EventType.POTENTIAL_CONFLICT -> Triple(ConflictRed, Icons.Default.Warning, "Potential conflict")
        EventType.RESOLUTION -> Triple(PrimaryTeal, Icons.Default.CheckCircle, "Resolved")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        // Vertical Timeline Column (Node + Line)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(36.dp)
        ) {
            // Node circle
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(nodeColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(nodeColor)
                )
            }

            // Connecting stem line
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(84.dp)
                        .background(
                            if (event.type == EventType.POTENTIAL_CONFLICT) ConflictBorder
                            else LightSurfaceBorder
                        )
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Content Card
        Surface(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 0.dp else 16.dp),
            shape = RoundedCornerShape(18.dp),
            color = if (event.type == EventType.POTENTIAL_CONFLICT) ConflictContainer.copy(alpha = 0.5f)
                    else LightSurfaceVariant.copy(alpha = 0.7f),
            border = BorderStroke(
                1.dp,
                if (event.type == EventType.POTENTIAL_CONFLICT) ConflictBorder
                else LightSurfaceBorder
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                // Header: Badge & Timestamp
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = nodeColor,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = badgeLabel,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = nodeColor
                        )
                    }

                    Text(
                        text = event.timestamp,
                        fontSize = 11.sp,
                        color = LightTextMuted
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Speaker name
                Text(
                    text = event.speaker,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Raw Quote
                Text(
                    text = "\"${event.quote}\"",
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic,
                    color = LightTextSecondary,
                    lineHeight = 18.sp
                )

                // Optional context note
                if (event.contextNote != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = event.contextNote,
                        fontSize = 11.sp,
                        color = if (event.type == EventType.POTENTIAL_CONFLICT) ConflictRed else PrimaryTeal,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
