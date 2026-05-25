package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.LoginScreen
import com.example.ui.MainApplicationShell
import com.example.ui.RegisterScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.BrainViewModel
import com.example.viewmodel.BrainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Fetch application dependencies safely
        val repository = (application as BrainApplication).repository
        val viewModel: BrainViewModel by viewModels { BrainViewModelFactory(repository) }

        setContent {
            val activeUser by viewModel.activeUser.collectAsState()
            val settings by viewModel.settingsState.collectAsState()
            val txs by viewModel.transactions.collectAsState()
            val todosList by viewModel.todos.collectAsState()

            // Trigger real-time widget updates on background data changes
            LaunchedEffect(txs, todosList, settings) {
                com.example.widget.BrainAppWidgetProvider.triggerUpdate(this@MainActivity)
            }

            val colorStyle = if (settings.dashboardOrder.contains("|")) {
                settings.dashboardOrder.split("|").getOrNull(0) ?: "PURPLE"
            } else {
                "PURPLE"
            }

            // Root Application theme connected to customizable settings preferences
            MyApplicationTheme(
                themeMode = settings.themeMode,
                colorStyle = colorStyle
            ) {
                val navController = rememberNavController()

                // Route sync triggers on active user alterations
                LaunchedEffect(activeUser) {
                    val user = activeUser
                    if (user != null && user.isSessionActive) {
                        if (navController.currentDestination?.route != "main") {
                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                                popUpTo("register") { inclusive = true }
                            }
                        }
                    } else {
                        val currentRoute = navController.currentDestination?.route
                        if (currentRoute != null && currentRoute != "login" && currentRoute != "register") {
                            navController.navigate("login") {
                                popUpTo("main") { inclusive = true }
                            }
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = "login",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("login") {
                        LoginScreen(
                            viewModel = viewModel,
                            onNavigateToRegister = { navController.navigate("register") },
                            onLoginSuccess = {
                                navController.navigate("main") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("register") {
                        RegisterScreen(
                            viewModel = viewModel,
                            onNavigateToLogin = { navController.navigate("login") },
                            onRegisterSuccess = {
                                navController.navigate("main") {
                                    popUpTo("register") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("main") {
                        MainApplicationShell(
                            viewModel = viewModel,
                            onLogoutRequested = {
                                navController.navigate("login") {
                                    popUpTo("main") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
