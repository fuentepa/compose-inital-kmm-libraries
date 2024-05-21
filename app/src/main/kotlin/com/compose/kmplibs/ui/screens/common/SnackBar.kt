package com.compose.kmplibs.ui.screens.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun SnackbarHostState.ShowSimpleSnackbar(message: String) {
    if (message.isNotEmpty()) {
        LaunchedEffect(Unit) {
            with(this@ShowSimpleSnackbar) {
                this.currentSnackbarData?.visuals
                showSnackbar(message, null, false, SnackbarDuration.Long)
            }
        }
    }
}

@Composable
fun ShowSnackBar(
    message: String,
    snackBarHostState: SnackbarHostState
) {
    if (message.isNotEmpty()) {
        LaunchedEffect(Unit) {
            snackBarHostState.showSnackbar(message)
        }
    }
}
