package com.compose.kmplibs.ui

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.compose.kmplibs.data.datasources.features.preferences.AppPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun rememberAppState(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    preferencesRepository: AppPreferencesRepository = koinInject()
): AppState {
    val isDarkTheme by preferencesRepository.isDarkTheme().collectAsState(initial = false)
    Log.d("rememberAppState", "isDarkTheme = $isDarkTheme")
    
    return remember(drawerState, coroutineScope, isDarkTheme) {
        AppState(
            drawerState = drawerState,
            coroutineScope = coroutineScope,
            darkTheme = isDarkTheme
        )
    }
}

class AppState(
    val drawerState: DrawerState,
    val coroutineScope: CoroutineScope,
    val darkTheme: Boolean = false
) {
    companion object {
        val DRAWER_OPTIONS = listOf("Home", "Settings", "Favorites", "Profile")
    }

    fun onMenuClick() {
        coroutineScope.launch { 
            if (drawerState.isOpen) {
                drawerState.close() 
            } else {
                drawerState.open()
            }
        }
    }
}