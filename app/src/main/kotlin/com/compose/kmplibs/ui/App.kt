package com.compose.kmplibs.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.compose.kmplibs.R
import com.compose.kmplibs.ui.navigation.AppNavHost
import com.compose.kmplibs.ui.navigation.NavDestinations
import com.compose.kmplibs.ui.theme.AppTheme
import com.compose.kmplibs.ui.util.getNavigationTypeForWindowInfo
import org.koin.compose.KoinContext

@Composable
fun App(appState: AppState = rememberAppState()) {
    KoinContext {
        AppTheme(
            darkTheme = appState.darkTheme
        ) {
            AdaptiveApp(appState)
        }
    }
}

@Composable
fun AdaptiveApp(appState: AppState) {
    val navController = rememberNavController()
    var selectedDestination by rememberSaveable { mutableStateOf(AppDestinations.Movies) }
    
    // Obtener información adaptativa de la ventana actual
    val windowInfo = currentWindowAdaptiveInfo()
    
    NavigationSuiteScaffold(
        layoutType = getNavigationTypeForWindowInfo(windowInfo),
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destination ->
                with(destination) {
                    item(
                        icon = { Icon(icon, LocalContext.current.getString(contentDescription))},
                        label = { Text(LocalContext.current.getString(label)) },
                        selected = destination == selectedDestination,
                        onClick = { selectedDestination = destination }
                    )
                }
            }
        }
    ) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AppNavHost(navController, selectedDestination.navDestination)
        }
    }
}


enum class AppDestinations(val label: Int, val icon: ImageVector, val contentDescription: Int, val navDestination: NavDestinations) {
    Movies(R.string.movies, Icons.Default.Home, R.string.movies, NavDestinations.Home),
    Settings(R.string.configuration, Icons.Default.Settings, R.string.configuration, NavDestinations.Settings);
}