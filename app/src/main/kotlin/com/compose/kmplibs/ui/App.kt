package com.compose.kmplibs.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.compose.kmplibs.R
import com.compose.kmplibs.ui.navigation.AppBarIcon
import com.compose.kmplibs.ui.navigation.TheTopAppBar
import com.compose.kmplibs.ui.screens.films.FilmsScreen
import com.compose.kmplibs.ui.theme.ComposeinitalkmmlibrariesTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Preview
@Composable
fun App(appState: AppState = rememberAppState()) {
    KoinAndroidContext {
        AppScreen {
            ModalNavigationDrawer(
                drawerState = appState.drawerState,
                drawerContent = {}
            ) {
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
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        FilmsScreen(onClick = {
                            Snackbar {
                                Text(text = it.title)
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
