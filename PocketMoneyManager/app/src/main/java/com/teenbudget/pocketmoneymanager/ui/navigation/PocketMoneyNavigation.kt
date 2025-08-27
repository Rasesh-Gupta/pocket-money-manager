package com.teenbudget.pocketmoneymanager.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.teenbudget.pocketmoneymanager.ui.screens.*

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Transactions : Screen("transactions", "Transactions", Icons.Filled.List)
    object Goals : Screen("goals", "Goals", Icons.Filled.Star)
    object Profile : Screen("profile", "Profile", Icons.Filled.Person)
    object AddTransaction : Screen("add_transaction", "Add Transaction", Icons.Filled.Add)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PocketMoneyNavigation() {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Transactions, Screen.Goals, Screen.Profile)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Transactions.route) { TransactionsScreen(navController) }
            composable(Screen.Goals.route) { GoalsScreen(navController) }
            composable(Screen.Profile.route) { ProfileScreen(navController) }
            composable(Screen.AddTransaction.route) { AddTransactionScreen(navController) }
        }
    }
}