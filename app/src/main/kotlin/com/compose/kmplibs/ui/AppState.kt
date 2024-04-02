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
    scaffoldState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState = remember(scaffoldState, coroutineScope) {
    AppState(scaffoldState,  coroutineScope)
}
class AppState(
    val drawerState: DrawerState,
    val coroutineScope: CoroutineScope,
) {

    fun onMenuClick() {
        coroutineScope.launch { drawerState.open() }
    }
}