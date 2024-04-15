package com.compose.kmplibs.ui.screens.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.usecases.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MovieDetailsViewModel (private val getMovieDetailsUseCase: GetMovieDetailsUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    data class UiState(
        val loading: Boolean = false,
        // TODO: Review
        val movieDetails: Movie? = null,
    )

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            // TODO: Retirieve ID!!
            getMovieDetailsUseCase.invoke(1).fold({ }) {
                _state.value = UiState(movieDetails = it)
            }
        }
    }
}