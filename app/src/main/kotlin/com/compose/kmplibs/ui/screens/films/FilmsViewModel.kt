package com.compose.kmplibs.ui.screens.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.kmplibs.data.entity.Film
import com.compose.kmplibs.usecases.GetAllFilmsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class FilmsViewModel(private val getAllFilmsUseCase: GetAllFilmsUseCase) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    data class UiState(
        val loading: Boolean = false,
        val films: List<Film> = emptyList()
    )

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            getAllFilmsUseCase().fold( { }) {
                _state.value = UiState(films = it)
            }
        }
    }
}