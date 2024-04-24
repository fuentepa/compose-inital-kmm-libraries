package com.compose.kmplibs.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.compose.kmplibs.R
import com.compose.kmplibs.ui.navigation.AppBarIcon
import com.compose.kmplibs.ui.navigation.TheTopAppBar
import com.compose.kmplibs.ui.screens.movies.MoviesScreen
import com.compose.kmplibs.ui.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.compose.KoinContext


@Preview
@Composable
fun App(appState: AppState = rememberAppState()) {
    KoinContext {
        AppTheme {
            AppScreen {
                ModalNavigationDrawer(
                    drawerState = appState.drawerState,
                    drawerContent = {}
                ) {
                    Scaffold(
                        topBar = {
                            TheTopAppBar(
                                title = { Text(stringResource(id = R.string.screen_movies_title)) },
                                navigationIcon = {
                                    AppBarIcon(
                                        imageVector = Icons.Default.Menu,
                                        onClick = { appState.onMenuClick() }
                                    )
                                }
                            )
                        }
                    ) { padding ->
                        Navigator(screen = MoviesScreen()) { navigator ->
                            SlideTransition(
                                navigator = navigator,
                                modifier = Modifier.padding(padding)
                                )
                        }
                    }
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
