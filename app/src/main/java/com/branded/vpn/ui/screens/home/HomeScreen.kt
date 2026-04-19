package com.branded.vpn.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.branded.vpn.core.domain.model.VpnStatus
import com.branded.vpn.ui.components.VpnButton

@Composable
fun HomeScreen(
    onNavigateToServers: () -> Unit,
    onNavigateToAuth: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = when (uiState.vpnStatus) {
                is VpnStatus.Connected -> "Secured"
                is VpnStatus.Connecting -> "Connecting..."
                else -> "Not Protected"
            },
            style = MaterialTheme.typography.headlineLarge
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        VpnButton(
            status = uiState.vpnStatus,
            onClick = { viewModel.toggleConnection() }
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(onClick = onNavigateToServers) {
            Text(uiState.selectedNode?.name ?: "Select Server")
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Down: ${uiState.trafficStats.downSpeed}")
            Text("Up: ${uiState.trafficStats.upSpeed}")
        }
    }
}
