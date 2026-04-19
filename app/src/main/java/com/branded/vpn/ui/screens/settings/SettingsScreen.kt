package com.branded.vpn.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.branded.vpn.R

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val autoConnect by viewModel.autoConnect.collectAsState(initial = false)
    val protocol by viewModel.protocol.collectAsState(initial = "VLESS")
    val appTheme by viewModel.appTheme.collectAsState(initial = "SYSTEM")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp)
    ) {
        Text(stringResource(R.string.nav_settings), style = MaterialTheme.typography.displayLarge)
        
        Spacer(modifier = Modifier.height(40.dp))

        // Theme Selection
        Text(stringResource(R.string.appearance), style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        val themeOptions = listOf(
            "SYSTEM" to R.string.theme_system,
            "LIGHT" to R.string.theme_light,
            "DARK" to R.string.theme_dark
        )
        themeOptions.forEach { (mode, labelRes) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(selected = appTheme == mode, onClick = { viewModel.setTheme(mode) })
                Text(stringResource(labelRes))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(stringResource(R.string.auto_connect), style = MaterialTheme.typography.titleLarge)
                Text(stringResource(R.string.auto_connect_desc), style = MaterialTheme.typography.bodySmall)
            }
            Switch(
                checked = autoConnect,
                onCheckedChange = { viewModel.toggleAutoConnect(it) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(stringResource(R.string.tunnel_protocol), style = MaterialTheme.typography.titleLarge)
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
        
        Text("Version 1.1.0-beta", modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}
