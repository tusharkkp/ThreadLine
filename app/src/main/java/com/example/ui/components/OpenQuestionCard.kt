package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.OpenQuestion
import com.example.ui.theme.*

@Composable
fun OpenQuestionCard(
    question: OpenQuestion,
    onResolve: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .testTag("question_card_${question.id}"),
        shape = RoundedCornerShape(20.dp),
        color = if (question.isResolved) LightSurfaceVariant.copy(alpha = 0.6f)
                else MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            1.dp,
            if (question.isResolved) LightSurfaceBorder.copy(alpha = 0.5f)
            else WarmAccentBorder
        ),
        tonalElevation = if (question.isResolved) 0.dp else 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (question.isResolved) LightSurfaceVariant else WarmAccentContainer
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = null,
                            tint = if (question.isResolved) LightTextMuted else OnWarmAccentContainer,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = if (question.isResolved) "RESOLVED" else "OPEN QUESTION",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (question.isResolved) LightTextMuted else OnWarmAccentContainer
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = LightTextMuted,
                        modifier = Modifier.size(11.dp)
                    )
                    Text(
                        text = question.timestamp,
                        fontSize = 11.sp,
                        color = LightTextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = question.question,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Asked by ${question.askedBy}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryTeal
            )

            if (question.contextQuote.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = LightSurfaceVariant
                ) {
                    Text(
                        text = "\"${question.contextQuote}\"",
                        fontSize = 11.sp,
                        fontStyle = FontStyle.Italic,
                        color = LightTextSecondary,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            if (question.isResolved && question.resolutionNote != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "✓ ${question.resolutionNote}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryTeal
                )
            } else if (!question.isResolved) {
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = onResolve,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.align(Alignment.End),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Resolve", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
