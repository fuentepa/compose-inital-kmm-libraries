package com.compose.kmplibs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.kmplibs.ui.App
import com.compose.kmplibs.usecases.GetIsDarkThemeUseCase
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val isDarkThemeUseCase: GetIsDarkThemeUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {
                setContent {
                    val isDarkTheme by isDarkThemeUseCase().collectAsStateWithLifecycle(initialValue = null)

                    isDarkTheme?.let { darkTheme ->
                        App(initialDarkTheme = darkTheme)
                    }
                }
            }
    }
}