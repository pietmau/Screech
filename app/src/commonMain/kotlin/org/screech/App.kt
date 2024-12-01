package org.screech

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.screech.login.presentation.ui.LoginScreen

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Login
        ) {
            composable<Login> { LoginScreen() }
        }
    }
}