package com.compose.kmplibs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.kmplibs.data.datasources.features.preferences.AppPreferencesRepository
import com.compose.kmplibs.ui.App
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val preferencesRepository: AppPreferencesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {
                val isDarkTheme by preferencesRepository.isDarkTheme()
                    .collectAsStateWithLifecycle(initialValue = false)

                App(initialDarkTheme = isDarkTheme)
            }
    }

}