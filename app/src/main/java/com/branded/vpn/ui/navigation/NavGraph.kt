package com.branded.vpn.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.branded.vpn.ui.screens.home.HomeScreen
import com.branded.vpn.ui.screens.auth.AuthScreen
import com.branded.vpn.ui.screens.servers.ServersScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToServers = { navController.navigate(Screen.Servers.route) },
                onNavigateToAuth = { navController.navigate(Screen.Auth.route) }
            )
        }
        composable(Screen.Auth.route) {
            AuthScreen(onAuthSuccess = { navController.popBackStack() })
        }
        composable(Screen.Servers.route) {
            ServersScreen(onBack = { navController.popBackStack() })
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Auth : Screen("auth")
    object Servers : Screen("servers")
    object Subscription : Screen("subscription")
    object Settings : Screen("settings")
}
