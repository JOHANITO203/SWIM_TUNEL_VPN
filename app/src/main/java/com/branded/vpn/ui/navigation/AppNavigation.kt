package com.branded.vpn.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.branded.vpn.ui.screens.auth.AuthScreen
import com.branded.vpn.ui.screens.help.SupportScreen
import com.branded.vpn.ui.screens.home.HomeScreen
import com.branded.vpn.ui.screens.servers.ServersScreen
import com.branded.vpn.ui.screens.settings.SettingsScreen
import com.branded.vpn.ui.screens.subscription.SubscriptionScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Servers : Screen("servers", "Nodes", Icons.Default.Language)
    object Subscription : Screen("subscription", "Plan", Icons.Default.List)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
    object Auth : Screen("auth", "Auth", Icons.Default.Home) // Not in bottom bar
    object Support : Screen("support", "Support", Icons.Default.Home) // Not in bottom bar
}

val BottomNavItems = listOf(
    Screen.Home,
    Screen.Servers,
    Screen.Subscription,
    Screen.Settings
)

@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentDestination: NavDestination?
) {
    // Only show bottom bar for main sections
    val showBar = BottomNavItems.any { it.route == currentDestination?.route }
    if (!showBar) return

    NavigationBar {
        BottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToServers = { navController.navigate(Screen.Servers.route) },
                onNavigateToAuth = { navController.navigate(Screen.Auth.route) }
            )
        }
        composable(Screen.Servers.route) {
            ServersScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Subscription.route) {
            SubscriptionScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
        composable(Screen.Auth.route) {
            AuthScreen(onAuthSuccess = { navController.popBackStack() })
        }
        composable(Screen.Support.route) {
            SupportScreen()
        }
    }
}
