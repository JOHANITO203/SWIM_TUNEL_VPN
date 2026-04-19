package com.branded.vpn.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.branded.vpn.ui.screens.auth.AuthScreen
import com.branded.vpn.ui.screens.help.SupportScreen
import com.branded.vpn.ui.screens.home.HomeScreen
import com.branded.vpn.ui.screens.servers.ServersScreen
import com.branded.vpn.ui.screens.settings.SettingsScreen
import com.branded.vpn.ui.screens.subscription.SubscriptionScreen
import com.branded.vpn.ui.screens.vpn.VpnPermissionScreen

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
        composable(Screen.VpnPermission.route) {
            VpnPermissionScreen(onPermissionGranted = { navController.popBackStack() })
        }
    }
}
