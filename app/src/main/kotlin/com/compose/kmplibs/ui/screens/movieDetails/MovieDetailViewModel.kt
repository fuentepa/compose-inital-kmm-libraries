package com.compose.kmplibs.ui.screens.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.kmplibs.data.model.MovieDetail
import com.compose.kmplibs.ui.screens.common.Event
import com.compose.kmplibs.ui.screens.common.UIState
import com.compose.kmplibs.usecases.GetMovieDetailUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MovieDetailViewModel(
    private val movieId: Int,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<MovieDetail?>>(UIState.Loading())
    val uiState = _uiState.asStateFlow()

    private val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            try {
                getMovieDetailUseCase(movieId).fold(
                    { data -> _uiState.value = UIState.Success(data) },
                    { error -> eventChannel.send(Event.OnError(error.localizedMessage ?: "Unknown error")) }
                )
            } catch (e: Exception) {
                eventChannel.send(Event.OnError(e.localizedMessage ?: "Unknown error"))
            }
        }
    }
}