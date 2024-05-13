package com.compose.kmplibs.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.ui.screens.common.UIState
import com.compose.kmplibs.usecases.GetTopRatedMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MoviesViewModel(private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<UIState<List<Movie>>>(UIState.Loading())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getTopRatedMoviesUseCase().fold({
                _state.value = UIState.Error(it.toString())
            }) {
                _state.value = UIState.Success(it)
            }
        }
    }
}