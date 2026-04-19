package com.branded.vpn.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Servers : Screen("servers", "Nodes", Icons.Default.Language)
    object Subscription : Screen("subscription", "Plan", Icons.Default.List)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
    object Auth : Screen("auth", "Auth", Icons.Default.Home)
    object Support : Screen("support", "Support", Icons.Default.Home)
    object VpnPermission : Screen("vpn_permission", "Permission", Icons.Default.Home)
}

val BottomNavItems = listOf(
    Screen.Home,
    Screen.Servers,
    Screen.Subscription,
    Screen.Settings
)
