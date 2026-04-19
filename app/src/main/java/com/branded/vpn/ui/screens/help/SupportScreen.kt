package com.branded.vpn.ui.screens.help

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HelpCenter
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SupportScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Help & Support", style = MaterialTheme.typography.displayLarge)
        
        Spacer(modifier = Modifier.height(40.dp))

        SupportOption(Icons.Default.HelpCenter, "Knowledge Base", "Guides on how to use VPN")
        SupportOption(Icons.Default.Message, "Live Chat", "Talk to our team (24/7)")
        SupportOption(Icons.Default.Email, "Email Us", "support@brandedvpn.com")

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* Contact support action */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Contact Support")
        }
    }
}

@Composable
fun SupportOption(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Text(subtitle, style = MaterialTheme.typography.bodySmall)
        }
    }
}
