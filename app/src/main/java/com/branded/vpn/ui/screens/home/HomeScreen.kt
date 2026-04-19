package com.branded.vpn.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material.icons.outlined.Public
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.branded.vpn.R
import com.branded.vpn.core.domain.model.VpnStatus

@Composable
fun HomeScreen(
    onNavigateToServers: () -> Unit,
    onNavigateToAuth: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Atmospheric background glow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            when (uiState.vpnStatus) {
                                is VpnStatus.Connected -> StatusGreen.copy(alpha = 0.15f)
                                is VpnStatus.Disconnected -> Color.Transparent
                                else -> StatusOrange.copy(alpha = 0.15f)
                            },
                            Color.Transparent
                        ),
                        center = androidx.compose.ui.geometry.Offset(500f, 1000f),
                        radius = 1200f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 4.sp,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(64.dp))

            // Status indicator
            AnimatedContent(
                targetState = uiState.vpnStatus,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "statusContent"
            ) { status ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = when (status) {
                            is VpnStatus.Connected -> stringResource(R.string.status_protected)
                            is VpnStatus.Connecting -> stringResource(R.string.status_connecting)
                            is VpnStatus.Disconnecting -> stringResource(R.string.status_disconnecting)
                            is VpnStatus.Disconnected -> stringResource(R.string.status_unprotected)
                            is VpnStatus.Error -> stringResource(R.string.status_error)
                        },
                        color = when (status) {
                            is VpnStatus.Connected -> StatusGreen
                            is VpnStatus.Disconnected -> MaterialTheme.colorScheme.onSurfaceVariant
                            is VpnStatus.Error -> StatusRed
                            else -> StatusOrange
                        },
                        style = MaterialTheme.typography.labelMedium,
                        letterSpacing = 2.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Main Power Button
            Box(contentAlignment = Alignment.Center) {
                // Secondary glow
                if (uiState.vpnStatus !is VpnStatus.Disconnected) {
                    Box(
                        modifier = Modifier
                            .size(260.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        when (uiState.vpnStatus) {
                                            is VpnStatus.Connected -> StatusGreen.copy(alpha = pulseAlpha)
                                            else -> StatusOrange.copy(alpha = pulseAlpha)
                                        },
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                }
                
                Surface(
                    onClick = { viewModel.toggleVpn() },
                    modifier = Modifier.size(200.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    shadowElevation = 12.dp,
                    border = BorderStroke(
                        width = 1.dp,
                        color = when (uiState.vpnStatus) {
                            is VpnStatus.Connected -> StatusGreen.copy(alpha = 0.5f)
                            is VpnStatus.Disconnected -> MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                            else -> StatusOrange.copy(alpha = 0.5f)
                        }
                    )
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Outlined.PowerSettingsNew,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = when (uiState.vpnStatus) {
                                is VpnStatus.Connected -> StatusGreen
                                is VpnStatus.Disconnected -> MaterialTheme.colorScheme.onSurfaceVariant
                                is VpnStatus.Error -> StatusRed
                                else -> StatusOrange
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            // Node selector - Premium style
            Surface(
                onClick = onNavigateToServers,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Public,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            stringResource(R.string.selected_node),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            uiState.selectedNode?.name ?: stringResource(R.string.select_server_hint),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Traffic summary - Cleaner look
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TrafficIndicator(stringResource(R.string.download), uiState.trafficStats.downSpeed, StatusGreen)
                TrafficIndicator(stringResource(R.string.upload), uiState.trafficStats.upSpeed, MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun TrafficIndicator(label: String, speed: String, accentColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            label.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            speed,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Black,
            color = accentColor
        )
    }
}
