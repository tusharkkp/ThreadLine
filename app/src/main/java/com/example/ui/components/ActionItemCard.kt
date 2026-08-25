package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ActionItem
import com.example.model.ActionStatus
import com.example.ui.theme.*

@Composable
fun ActionItemCard(
    action: ActionItem,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isCompleted = action.status == ActionStatus.COMPLETED

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onToggle)
            .testTag("action_card_${action.id}"),
        shape = RoundedCornerShape(20.dp),
        color = if (isCompleted) LightSurfaceVariant.copy(alpha = 0.6f)
                else MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            1.dp,
            if (isCompleted) LightSurfaceBorder.copy(alpha = 0.5f)
            else LightSurfaceBorder
        ),
        tonalElevation = if (isCompleted) 0.dp else 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Checkbox Circle
            IconButton(
                onClick = onToggle,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        if (isCompleted) PrimaryTeal
                        else LightSurfaceVariant
                    )
            ) {
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Task description
                Text(
                    text = action.task,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isCompleted) LightTextMuted
                            else MaterialTheme.colorScheme.onSurface,
                    textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Owner & Deadline info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Owner chip
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (action.isMyTask) PrimaryContainer
                                else LightSurfaceVariant
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                tint = if (action.isMyTask) PrimaryTeal else LightTextSecondary,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = if (action.isMyTask) "Me (${action.owner})" else action.owner,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (action.isMyTask) OnPrimaryContainer else LightTextSecondary
                            )
                        }
                    }

                    // Deadline chip
                    if (action.deadline != null) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = WarmAccentContainer
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = null,
                                    tint = OnWarmAccentContainer,
                                    modifier = Modifier.size(11.dp)
                                )
                                Text(
                                    text = action.deadline,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnWarmAccentContainer
                                )
                            }
                        }
                    }
                }

                // Source quote preview
                if (action.sourceQuote.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"${action.sourceQuote}\"",
                        fontSize = 11.sp,
                        fontStyle = FontStyle.Italic,
                        color = LightTextMuted
                    )
                }
            }
        }
    }
}
