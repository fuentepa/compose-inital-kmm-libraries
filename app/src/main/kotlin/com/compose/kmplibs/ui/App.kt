package com.compose.kmplibs.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.compose.kmplibs.ui.navigation.AppNavigation
import com.compose.kmplibs.ui.theme.AppTheme
import org.koin.compose.KoinContext

@Composable
fun App(appState: AppState = rememberAppState()) {
    KoinContext {
        AppTheme {
            AppScreen {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun AppScreen(content: @Composable () -> Unit) {
    AppTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}