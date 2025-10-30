package com.compose.kmplibs.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.kmplibs.ui.screens.common.Event
import com.compose.kmplibs.ui.screens.common.UIState
import com.compose.kmplibs.usecases.GetIsDarkThemeUseCase
import com.compose.kmplibs.usecases.SetIsDarkThemeUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

data class SettingsState(
    val isDarkMode: Boolean = false
)

@KoinViewModel
class SettingsViewModel(
    private val setIsDarkThemeUseCase: SetIsDarkThemeUseCase,
    private val getIsDarkThemeUseCase: GetIsDarkThemeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<SettingsState>>(UIState.Loading())
    val uiState: StateFlow<UIState<SettingsState>> = _uiState.asStateFlow()

    private val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    init {
        loadPreferences()
    }

    private fun loadPreferences() {
        viewModelScope.launch {
            getIsDarkThemeUseCase().collect { isDarkMode ->
                _uiState.value = UIState.Success(SettingsState(isDarkMode))
            }
        }
    }

    fun toggleDarkMode() {
        viewModelScope.launch {
            val currentState = (_uiState.value as? UIState.Success)?.data ?: return@launch
            val newDarkModeState = !currentState.isDarkMode
            setIsDarkThemeUseCase(newDarkModeState)
        }
    }
} 