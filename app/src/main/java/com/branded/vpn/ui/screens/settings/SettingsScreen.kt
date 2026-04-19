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

import androidx.compose.foundation.background
import androidx.compose.ui.text.font.FontWeight

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
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(24.dp)
    ) {
        Text(
            stringResource(R.string.nav_settings),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Black
        )
        
        Spacer(modifier = Modifier.height(32.dp))

        // Appearance Group
        SettingsGroup(title = stringResource(R.string.appearance)) {
            val themeOptions = listOf(
                "SYSTEM" to R.string.theme_system,
                "LIGHT" to R.string.theme_light,
                "DARK" to R.string.theme_dark
            )
            themeOptions.forEach { (mode, labelRes) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    RadioButton(
                        selected = appTheme == mode,
                        onClick = { viewModel.setTheme(mode) },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(labelRes), style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Connection Group
        SettingsGroup(title = stringResource(R.string.auto_connect)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(R.string.auto_connect),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        stringResource(R.string.auto_connect_desc),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = autoConnect,
                    onCheckedChange = { viewModel.toggleAutoConnect(it) },
                    colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Protocol Group
        SettingsGroup(title = stringResource(R.string.tunnel_protocol)) {
            val protocols = listOf("VLESS", "VMESS", "TROJAN", "SHADOWSOCKS")
            protocols.forEach { p ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    RadioButton(
                        selected = protocol == p,
                        onClick = { viewModel.setProtocol(p) },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(p, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Text(
            "Version 1.1.0-beta",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun SettingsGroup(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            title.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}
