package com.branded.vpn.ui.screens.help

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HelpCenter
import androidx.compose.material.icons.filled.Message
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SupportScreen() {
    val faqs = listOf(
        FAQ("How to connect?", "Simply tap the power button on the home screen to connect to the fastest available node."),
        FAQ("Why is it slow?", "Connection speed depends on your physical distance from the selected node and the node's current load."),
        FAQ("Is my data secure?", "Yes, we use industry-standard encryption protocols (VLESS/VMess) to ensure your traffic is protected."),
        FAQ("How to cancel subscription?", "You can manage your subscription directly from the Profile tab.")
    )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        item {
            Text("Help & Support", style = MaterialTheme.typography.displaySmall)
            Spacer(modifier = Modifier.height(32.dp))
            
            Text("Contact Options", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            SupportCard {
                Column {
                    SupportOption(Icons.Default.HelpCenter, "Knowledge Base", "Guides on how to use VPN")
                    SupportOption(Icons.Default.Message, "Live Chat", "Talk to our team (24/7)")
                    SupportOption(Icons.Default.Email, "Email Us", "support@swimtunel.com")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            Text("Frequently Asked Questions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(faqs) { faq ->
            FAQItem(faq)
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = { /* Contact support action */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Open Support Ticket")
            }
        }
    }
}

@Composable
fun SupportCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
fun FAQItem(faq: FAQ) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        onClick = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(faq.question, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(faq.answer, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

data class FAQ(val question: String, val answer: String)

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
