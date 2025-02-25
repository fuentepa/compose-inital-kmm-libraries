package com.compose.kmplibs.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberAppState(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState = remember(drawerState, coroutineScope) {
    AppState(drawerState,  coroutineScope)
}

class AppState(
    val drawerState: DrawerState,
    val coroutineScope: CoroutineScope,
) {
    companion object {
        val DRAWER_OPTIONS = listOf("Home", "Settings", "Exit")
    }

    fun onMenuClick() {
        coroutineScope.launch { drawerState.open() }
    }

}