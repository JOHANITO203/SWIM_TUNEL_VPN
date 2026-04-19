package com.branded.vpn.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseSize"
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("SWIMTUNELVPN", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(40.dp))

        // Status indicator
        AnimatedContent(
            targetState = uiState.vpnStatus,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "statusContent"
        ) { status ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = when (status) {
                        is VpnStatus.Connected -> "PROTECTED"
                        is VpnStatus.Connecting -> "ESTABLISHING TUNNEL..."
                        is VpnStatus.Disconnecting -> "DISCONNECTING..."
                        is VpnStatus.Disconnected -> "UNPROTECTED"
                        is VpnStatus.Error -> "CONNECTION FAILED"
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
            if (uiState.vpnStatus is VpnStatus.Connecting) {
                Box(
                    modifier = Modifier
                        .size(200.dp * pulseSize)
                        .clip(CircleShape)
                        .border(2.dp, Color(0xFFFFA000).copy(alpha = 0.3f), CircleShape)
                )
            }
            
            Button(
                onClick = { viewModel.toggleVpn() },
                modifier = Modifier.size(200.dp).clip(CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (uiState.vpnStatus) {
                        is VpnStatus.Connected -> Color(0xFFE8F5E9)
                        is VpnStatus.Error -> Color(0xFFFFEBEE)
                        else -> Color(0xFFFFF3E0)
                    }
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Icon(
                    Icons.Default.PowerSettingsNew,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = when (uiState.vpnStatus) {
                        is VpnStatus.Connected -> Color(0xFF4CAF50)
                        is VpnStatus.Error -> Color(0xFFD32F2F)
                        else -> Color(0xFFE91E63)
                    }
                )
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
                    Text("Selected Node", style = MaterialTheme.typography.labelMedium)
                    Text(uiState.selectedNode?.name ?: "Click to select a server", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Traffic summary
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TrafficIndicator("Download", uiState.trafficStats.downSpeed)
            TrafficIndicator("Upload", uiState.trafficStats.upSpeed)
        }
    }
}

@Composable
fun TrafficIndicator(label: String, speed: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(speed, style = MaterialTheme.typography.bodyLarge, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
    }
}
