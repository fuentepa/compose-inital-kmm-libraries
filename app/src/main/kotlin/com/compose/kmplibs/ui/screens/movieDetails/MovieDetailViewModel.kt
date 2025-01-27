package com.compose.kmplibs.ui.screens.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.ui.screens.common.UIState
import com.compose.kmplibs.usecases.GetMovieDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MovieDetailViewModel(
    private val movieId: Int,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UIState<MovieDetail?>>(UIState.Loading())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                getMovieDetailUseCase(movieId).fold(
                    { error -> _state.value = UIState.Error(error.toString()) },
                    { data -> _state.value = UIState.Success(data) }
                )
            } catch (e: Exception) {
                _state.value = UIState.Error(error = e.localizedMessage ?: "Unknown error")
            }
        }
    }
}