package com.branded.vpn.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))
        
        SettingItem("VPN Protocol", "VLESS (Recommended)")
        SettingItem("Split Tunneling", "Off")
        SettingItem("Kill Switch", "Disabled")
        SettingItem("App Version", "1.0.0 (MVP)")
    }
}

@Composable
fun SettingItem(title: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(title, style = MaterialTheme.typography.bodyLarge)
        Text(value, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}
