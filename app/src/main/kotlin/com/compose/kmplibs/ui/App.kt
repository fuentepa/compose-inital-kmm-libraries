package com.compose.kmplibs.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.compose.kmplibs.ui.screens.movies.MoviesScreen
import com.compose.kmplibs.ui.theme.AppTheme
import org.koin.compose.KoinContext


@Preview
@Composable
fun App(appState: AppState = rememberAppState()) {
    KoinContext {
        AppTheme {
            AppScreen {
                Navigator(MoviesScreen()) { navigator ->
                    SlideTransition( navigator = navigator)
                }
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