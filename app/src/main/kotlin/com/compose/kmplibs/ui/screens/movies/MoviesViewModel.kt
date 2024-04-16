package com.compose.kmplibs.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.usecases.GetTopRatedMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MoviesViewModel(private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList(),
    )

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            getTopRatedMoviesUseCase().fold({ }) {
                _state.value = UiState(movies = it)
            }
        }
    }
}