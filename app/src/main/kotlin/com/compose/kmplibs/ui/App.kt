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
import com.compose.kmplibs.R
import com.compose.kmplibs.ui.navigation.AppBarIcon
import com.compose.kmplibs.ui.navigation.TheTopAppBar
import com.compose.kmplibs.ui.screens.movies.MoviesScreen
import com.compose.kmplibs.ui.theme.ComposeinitalkmmlibrariesTheme
import org.koin.compose.KoinContext


@Preview
@Composable
fun App(appState: AppState = rememberAppState()) {
    KoinContext {
        AppScreen {
            ModalNavigationDrawer(
                drawerState = appState.drawerState,
                drawerContent = {}
            ) {
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    topBar = {
                        TheTopAppBar(
                            title = { Text(stringResource(id = R.string.app_name)) },
                            navigationIcon = {
                                AppBarIcon(
                                    imageVector = Icons.Default.Menu,
                                    onClick = { appState.onMenuClick() }
                                )
                            }
                        )
                    },
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        MoviesScreen(onClick = {
                            //esto es para probar que el click funciona, realmente aqui seria una navegacion
                            LaunchedEffect(key1 = null) {
                                appState.coroutineScope.launch {
                                    snackbarHostState.showSnackbar(it.title)
                                }
                            }
                        })

                    }
                }
            }
        }
    }
}

@Composable
fun AppScreen(content: @Composable () -> Unit) {
    ComposeinitalkmmlibrariesTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}
