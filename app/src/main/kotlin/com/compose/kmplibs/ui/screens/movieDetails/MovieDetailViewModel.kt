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
class MovieDetailViewModel(private val movieId: Int,private val getMovieDetailUseCase: GetMovieDetailUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<UIState<MovieDetail>>(UIState.Loading())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getMovieDetailUseCase.invoke(movieId).fold({
                _state.value = UIState.Error(it.toString())
            }) {
                _state.value = UIState.Success(it)
            }
        }
    }
}