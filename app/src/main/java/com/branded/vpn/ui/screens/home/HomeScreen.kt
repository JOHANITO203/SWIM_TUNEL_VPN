package com.branded.vpn.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("SWIMTUNELVPN", style = MaterialTheme.typography.displayLarge)
        
        Spacer(modifier = Modifier.height(60.dp))

        // Status indicator
        Text(
            text = when (uiState.vpnStatus) {
                is VpnStatus.Connected -> "PROTECTED"
                is VpnStatus.Connecting -> "CONNECTING..."
                is VpnStatus.Disconnected -> "UNPROTECTED"
                else -> "ERROR"
            },
            color = when (uiState.vpnStatus) {
                is VpnStatus.Connected -> Color(0xFF4CAF50)
                is VpnStatus.Disconnected -> Color(0xFFF44336)
                else -> Color(0xFFFFA000)
            },
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Main Power Button
        Button(
            onClick = { viewModel.toggleVpn() },
            modifier = Modifier.size(200.dp).clip(CircleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (uiState.vpnStatus is VpnStatus.Connected) Color(0xFFE8F5E9) else Color(0xFFFFF3E0)
            )
        ) {
            Icon(
                Icons.Default.PowerSettingsNew,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = if (uiState.vpnStatus is VpnStatus.Connected) Color(0xFF4CAF50) else Color(0xFFE91E63)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

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
