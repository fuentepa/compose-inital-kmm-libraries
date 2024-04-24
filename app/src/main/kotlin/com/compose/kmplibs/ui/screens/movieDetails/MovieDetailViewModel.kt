package com.compose.kmplibs.ui.screens.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.usecases.GetMovieDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MovieDetailViewModel(private val movieId: Int,private val getMovieDetailUseCase: GetMovieDetailUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    data class UiState(
        val loading: Boolean = false,
        // TODO: Review
        val movieDetails: MovieDetail? = null,
    )

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            getMovieDetailUseCase.invoke(movieId).fold({ }) {
                _state.value = UiState(movieDetails = it)
            }
        }
    }
}