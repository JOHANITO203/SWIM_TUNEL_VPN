package com.branded.vpn.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.branded.vpn.R

sealed class Screen(val route: String, @StringRes val titleRes: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.nav_home, Icons.Default.Home)
    object Servers : Screen("servers", R.string.nav_nodes, Icons.Default.Language)
    object Profile : Screen("profile", R.string.nav_profile, Icons.Default.Person)
    object Settings : Screen("settings", R.string.nav_settings, Icons.Default.Settings)
    object Auth : Screen("auth", R.string.nav_home, Icons.Default.Home)
    object Support : Screen("support", R.string.support_title, Icons.Default.HelpCenter)
    object VpnPermission : Screen("vpn_permission", R.string.app_name, Icons.Default.Home)
}

val BottomNavItems = listOf(
    Screen.Home,
    Screen.Servers,
    Screen.Profile,
    Screen.Support,
    Screen.Settings
)
