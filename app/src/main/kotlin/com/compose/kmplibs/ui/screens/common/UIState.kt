package com.compose.kmplibs.ui.screens.common

import com.compose.kmplibs.data.entity.Movie

sealed class UIState<out T> {
    class Loading<T> : UIState<T>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error<T>(val error: String) : UIState<T>()
}