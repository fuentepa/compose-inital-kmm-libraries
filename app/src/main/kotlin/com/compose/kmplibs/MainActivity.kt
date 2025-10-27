package com.compose.kmplibs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.compose.kmplibs.data.datasources.features.preferences.AppPreferencesRepository
import com.compose.kmplibs.ui.App
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.annotation.Single

@Single
class MainActivity : ComponentActivity() {

    private val preferencesRepository: AppPreferencesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val isDarkTheme = preferencesRepository.isDarkTheme().first()

            setContent {
                App(initialDarkTheme = isDarkTheme)
            }
        }
    }

}