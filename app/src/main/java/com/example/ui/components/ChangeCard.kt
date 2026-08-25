package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DecisionChange
import com.example.ui.theme.*

@Composable
fun ChangeCard(
    change: DecisionChange,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .testTag("change_card_${change.id}"),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            1.dp,
            if (change.hasConflict) ConflictBorder
            else LightSurfaceBorder
        ),
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (change.hasConflict) ConflictContainer else PrimaryContainer
                ) {
                    Text(
                        text = if (change.hasConflict) "CONFLICT WARNING" else "CRITICAL CHANGE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (change.hasConflict) ConflictRed else PrimaryTeal,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                AiConfidenceBadge(confidence = change.confidence)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Change Transition Visual: Original -> New
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Original Pill
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = LightSurfaceVariant,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Original",
                            fontSize = 10.sp,
                            color = LightTextMuted
                        )
                        Text(
                            text = change.originalValue,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LightTextSecondary,
                            textDecoration = TextDecoration.LineThrough
                        )
                        Text(
                            text = change.originalTimestamp,
                            fontSize = 10.sp,
                            color = LightTextMuted
                        )
                    }
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Changed to",
                    tint = PrimaryTeal,
                    modifier = Modifier.size(16.dp)
                )

                // Updated Pill
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PrimaryContainer,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Updated",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryTeal
                        )
                        Text(
                            text = change.newValue,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnPrimaryContainer
                        )
                        Text(
                            text = change.updatedTimestamp,
                            fontSize = 10.sp,
                            color = PrimaryTeal.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Reason
            Text(
                text = "Why it changed:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = change.reason,
                fontSize = 12.sp,
                color = LightTextSecondary,
                lineHeight = 17.sp
            )

            // Conflict Warning if present
            if (change.hasConflict && change.conflictDescription != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(ConflictContainer.copy(alpha = 0.6f))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Conflict",
                        tint = ConflictRed,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "Potential conflict: ${change.conflictDescription}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ConflictRed
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Footer Link
            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "View decision history",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryTeal
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = PrimaryTeal,
                    modifier = Modifier.size(13.dp)
                )
            }
        }
    }
}
