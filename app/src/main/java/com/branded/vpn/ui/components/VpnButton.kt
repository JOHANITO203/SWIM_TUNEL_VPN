package com.branded.vpn.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.branded.vpn.core.domain.model.VpnStatus
import lucide_react.Power

@Composable
fun VpnButton(
    status: VpnStatus,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color = when (status) {
        is VpnStatus.Connected -> MaterialTheme.colorScheme.tertiary
        is VpnStatus.Connecting -> MaterialTheme.colorScheme.secondary
        is VpnStatus.Error -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = if (status is VpnStatus.Connecting) pulseAlpha else 1f))
            .clickable { onClick() }
    ) {
        // Since I can't use Lucide in Android directly without a wrapper or XML icons, 
        // I'll represent the Power icon with a simple box or assume a placeholder.
        // Actually, I'll use a simple indicator.
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f))
        )
    }
}
