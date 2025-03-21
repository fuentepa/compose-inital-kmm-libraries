package com.compose.kmplibs.ui.screens.settings

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.compose.kmplibs.R
import com.compose.kmplibs.ui.navigation.TheTopAppBar
import com.compose.kmplibs.ui.screens.common.CustomSnackbarHost
import com.compose.kmplibs.ui.screens.common.LoadingCircularIndicator
import com.compose.kmplibs.ui.screens.common.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen() {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(topBar = {
        TheTopAppBar(
            title = { Text(stringResource(R.string.configuration)) },
        )
    }, snackbarHost = { snackbarHostState.CustomSnackbarHost() }) { paddingValues ->

        SettingsContent(
            onErrorAction = {
                LaunchedEffect(Unit) {
                    with(snackbarHostState) {
                        currentSnackbarData?.dismiss()
                        showSnackbar(it)
                    }
                }
            }, modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    onErrorAction: @Composable (String) -> Unit = {},
    viewModel: SettingsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Log.d("TAG", "-> SettingsContent: state = $uiState")
    when (uiState) {
        is UIState.Error -> onErrorAction((uiState as UIState.Error).error)
        is UIState.Loading -> LoadingCircularIndicator(contentLoadingDescription = stringResource(R.string.loading_description_settings))
        is UIState.Success -> {
            (uiState as UIState.Success).data.let {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    BodyContent(isDarkMode = it.isDarkMode,
                        onDarkModeToggle = { viewModel.toggleDarkMode() })
                }
            }
        }
    }
}

@Composable
private fun BodyContent(
    isDarkMode: Boolean, onDarkModeToggle: () -> Unit
) {
    val darkModeEnabledText = stringResource(
        id = if (isDarkMode) R.string.dark_mode_enabled
        else R.string.dark_mode_disabled
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.screen_padding)
            )
    ) {
        Card(onClick = { onDarkModeToggle() }, //recomendado poner aqui por accesibilidad
            modifier = Modifier
                .semantics(mergeDescendants = true) {
                    role = Role.Switch
                    contentDescription = darkModeEnabledText
                }
                .fillMaxWidth()
                .minimumInteractiveComponentSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.item_padding)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.dark_mode),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = stringResource(R.string.dark_mode),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = dimensionResource(R.dimen.item_padding))
                    )
                }
                Switch(modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .semantics {
                        invisibleToUser()
                    }, checked = isDarkMode, onCheckedChange = { onDarkModeToggle() })
            }
        }
    }
}


