package com.branded.vpn.ui.screens.help

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HelpSupportScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Help & Support", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Common Issues:", style = MaterialTheme.typography.titleMedium)
        Text("1. Connection failed: Try switching to a different node.")
        Text("2. Slow speeds: Nodes with high load might be slower.")
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { /* Open support URL */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Contact Support")
        }
    }
}
