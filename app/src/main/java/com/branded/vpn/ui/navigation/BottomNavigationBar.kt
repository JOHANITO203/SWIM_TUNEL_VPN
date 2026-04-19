package com.branded.vpn.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.text.font.FontWeight

@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentDestination: NavDestination?
) {
    // Check if current route is part of bottom nav items using hierarchy for robustness
    val showBar = currentDestination?.hierarchy?.any { dest ->
        BottomNavItems.any { it.route == dest.route }
    } == true

    if (!showBar) return

    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        shadowElevation = 16.dp
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            windowInsets = WindowInsets(0, 0, 0, 0)
        ) {
            BottomNavItems.forEach { screen ->
                val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                NavigationBarItem(
                    icon = { 
                        Icon(
                            imageVector = when (screen.route) {
                                Screen.Home.route -> if (selected) Icons.Filled.Home else Icons.Outlined.Home
                                Screen.Servers.route -> if (selected) Icons.Filled.Dns else Icons.Outlined.Dns
                                Screen.Profile.route -> if (selected) Icons.Filled.AccountCircle else Icons.Outlined.AccountCircle
                                Screen.Settings.route -> if (selected) Icons.Filled.Settings else Icons.Outlined.Settings
                                Screen.Support.route -> if (selected) Icons.Filled.HelpCenter else Icons.Outlined.HelpCenter
                                else -> screen.icon
                            },
                            contentDescription = null
                        ) 
                    },
                    label = { 
                        Text(
                            stringResource(screen.titleRes),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        ) 
                    },
                    selected = selected,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}
