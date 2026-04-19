package com.branded.vpn.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.branded.vpn.data.local.SettingsDataStore

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val autoConnect by viewModel.autoConnect.collectAsState(initial = false)
    val protocol by viewModel.protocol.collectAsState(initial = "VLESS")

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("App Settings", style = MaterialTheme.typography.displayLarge)
        
        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Auto-Connect", style = MaterialTheme.typography.titleLarge)
                Text("Start VPN on device boot", style = MaterialTheme.typography.bodySmall)
            }
            Switch(
                checked = autoConnect,
                onCheckedChange = { viewModel.toggleAutoConnect(it) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Tunnel Protocol", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        
        val protocols = listOf("VLESS", "VMESS", "TROJAN", "SHADOWSOCKS")
        protocols.forEach { p ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(selected = protocol == p, onClick = { viewModel.setProtocol(p) })
                Text(p)
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Text("Version 1.0.0-mvp", modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}
