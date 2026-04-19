package com.branded.vpn.ui.screens.servers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Server") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(nodes) { node ->
                ServerItem(
                    node = node,
                    onClick = { 
                        viewModel.selectNode(node)
                        onBack()
                    }
                )
            }
        }
    }
}

@Composable
fun ServerItem(node: VpnNode, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(node.name) },
        supportingContent = { Text("Load: ${node.load}% | Ping: ${node.ping}ms") },
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        trailingContent = {
            Text(node.countryCode)
        }
    )
}
