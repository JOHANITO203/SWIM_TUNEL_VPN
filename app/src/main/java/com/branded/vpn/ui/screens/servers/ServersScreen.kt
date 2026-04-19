package com.branded.vpn.ui.screens.servers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.branded.vpn.core.domain.model.VpnNode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServersScreen(
    onBack: () -> Unit,
    viewModel: ServersViewModel = hiltViewModel()
) {
    val nodes by viewModel.nodes.collectAsState(initial = emptyList())
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Global Locations") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (isRefreshing) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            
            LazyColumn {
                items(nodes) { node ->
                    ServerListItem(node = node) {
                        viewModel.selectNode(node)
                        onBack()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerListItem(node: VpnNode, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier.fillMaxWidth(),
        headlineContent = { Text(node.name) },
        supportingContent = { Text("${node.protocol} | Ping: ${node.ping}ms") },
        trailingContent = {
            Column(horizontalAlignment = Alignment.End) {
                Text("${node.load}%", style = MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { node.load / 100f },
                    modifier = Modifier.width(60.dp),
                    color = when {
                        node.load > 80 -> MaterialTheme.colorScheme.error
                        node.load > 50 -> MaterialTheme.colorScheme.tertiary
                        else -> MaterialTheme.colorScheme.primary
                    },
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        },
        onClick = onClick
    )
}
