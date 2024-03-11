package com.compose.kmplibs.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.compose.kmplibs.R
import com.compose.kmplibs.ui.theme.ComposeinitalkmmlibrariesTheme


@Preview
@Composable
fun App() {
    ComposeinitalkmmlibrariesTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val appName = stringResource(id = R.string.app_name)
                Text("$appName")
            }
        }
    }
}