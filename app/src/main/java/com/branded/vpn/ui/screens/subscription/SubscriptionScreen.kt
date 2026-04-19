package com.branded.vpn.ui.screens.subscription

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SubscriptionScreen() {
    val planName = "Premium Unlimited"
    val expiry = System.currentTimeMillis() + 30L * 24 * 3600 * 1000
    val dateStr = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(expiry))

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Your Subscription", style = MaterialTheme.typography.displayLarge)
        
        Spacer(modifier = Modifier.height(40.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(planName, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Renews on: $dateStr")
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(onClick = { /* Handle upgrade */ }, modifier = Modifier.fillMaxWidth()) {
                    Text("Manage Plan")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Active Subscription URL", style = MaterialTheme.typography.labelSmall)
        Text("https://api.brandedvpn.com/v1/sub/v2/4482... (hidden)", color = MaterialTheme.colorScheme.primary)
    }
}
