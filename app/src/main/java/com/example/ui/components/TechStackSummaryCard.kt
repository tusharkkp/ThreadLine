package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TechStackItem
import com.example.ui.theme.*

@Composable
fun TechStackSummaryCard(
    techStack: List<TechStackItem>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, LightSurfaceBorder),
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Layers,
                    contentDescription = null,
                    tint = PrimaryTeal,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "CURRENT TECH STACK & STATE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryTeal,
                    letterSpacing = 0.8.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            techStack.forEachIndexed { index, item ->
                TechStackRow(item = item, onClick = { onItemClick(item.decisionId) })
                if (index < techStack.size - 1) {
                    HorizontalDivider(
                        color = LightSurfaceBorder,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TechStackRow(
    item: TechStackItem,
    onClick: () -> Unit
) {
    val isConflict = item.status.contains("Conflict", ignoreCase = true)
    val isDiscussion = item.status.contains("discussion", ignoreCase = true)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.category,
                fontSize = 11.sp,
                color = LightTextMuted,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = item.chosenTechnology,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (isConflict) ConflictRed else MaterialTheme.colorScheme.onSurface
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (isConflict) ConflictContainer
                        else if (isDiscussion) WarmAccentContainer
                        else PrimaryContainer
            ) {
                Text(
                    text = item.status,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isConflict) ConflictRed
                            else if (isDiscussion) OnWarmAccentContainer
                            else PrimaryTeal,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = LightTextMuted,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
