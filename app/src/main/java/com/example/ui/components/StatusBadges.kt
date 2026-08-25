package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DecisionStatus
import com.example.ui.theme.*

@Composable
fun DecisionStatusBadge(status: DecisionStatus, modifier: Modifier = Modifier) {
    val (bg, textColor, label, icon) = when (status) {
        DecisionStatus.CONFIRMED -> Quadruple(StatusConfirmedBg, StatusConfirmedText, "Confirmed", Icons.Default.CheckCircle)
        DecisionStatus.SUPERSEDED -> Quadruple(StatusChangedBg, StatusChangedText, "Changed", Icons.Default.SwapHoriz)
        DecisionStatus.CONFLICT_FLAGGED -> Quadruple(StatusConflictBg, StatusConflictText, "Conflict", Icons.Default.Warning)
        DecisionStatus.UNDER_DISCUSSION -> Quadruple(StatusOpenBg, StatusOpenText, "Under discussion", Icons.Default.HelpOutline)
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = textColor,
            modifier = Modifier.size(13.dp)
        )
        Text(
            text = label,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun AiConfidenceBadge(confidence: String = "High", modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(PrimaryContainer)
            .padding(horizontal = 8.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = PrimaryTeal,
            modifier = Modifier.size(11.dp)
        )
        Text(
            text = "AI: $confidence",
            color = OnPrimaryContainer,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
