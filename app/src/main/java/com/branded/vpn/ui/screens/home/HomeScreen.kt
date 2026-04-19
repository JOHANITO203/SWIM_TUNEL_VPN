package com.branded.vpn.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Public
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
    val pulseSize by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseSize"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 2.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))

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
                        is VpnStatus.Connected -> Color(0xFF4CAF50)
                        is VpnStatus.Disconnected -> Color(0xFFF44336)
                        is VpnStatus.Error -> MaterialTheme.colorScheme.error
                        else -> Color(0xFFFFA000)
                    },
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                
                if (status is VpnStatus.Error) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    ) {
                        Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            status.message,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Main Power Button with Pulsing Effect during connection
        Box(contentAlignment = Alignment.Center) {
            if (uiState.vpnStatus is VpnStatus.Connecting || uiState.vpnStatus is VpnStatus.Connected) {
                Box(
                    modifier = Modifier
                        .size(220.dp * pulseSize)
                        .clip(CircleShape)
                        .background(
                            if (uiState.vpnStatus is VpnStatus.Connected) Color(0xFF4CAF50).copy(alpha = 0.1f)
                            else Color(0xFFFFA000).copy(alpha = 0.1f)
                        )
                )
            }
            
            Surface(
                onClick = { viewModel.toggleVpn() },
                modifier = Modifier.size(180.dp),
                shape = CircleShape,
                color = when (uiState.vpnStatus) {
                    is VpnStatus.Connected -> Color(0xFFE8F5E9)
                    is VpnStatus.Error -> Color(0xFFFFEBEE)
                    else -> MaterialTheme.colorScheme.surfaceVariant
                },
                tonalElevation = 4.dp,
                shadowElevation = 8.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.PowerSettingsNew,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = when (uiState.vpnStatus) {
                            is VpnStatus.Connected -> Color(0xFF4CAF50)
                            is VpnStatus.Error -> Color(0xFFD32F2F)
                            else -> MaterialTheme.colorScheme.primary
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Node selector
        OutlinedCard(
            onClick = onNavigateToServers,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Public, contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(stringResource(R.string.selected_node), style = MaterialTheme.typography.labelMedium)
                    Text(uiState.selectedNode?.name ?: stringResource(R.string.select_server_hint), style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Traffic summary
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TrafficIndicator(stringResource(R.string.download), uiState.trafficStats.downSpeed)
            TrafficIndicator(stringResource(R.string.upload), uiState.trafficStats.upSpeed)
        }
    }
}

@Composable
fun TrafficIndicator(label: String, speed: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(speed, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}
