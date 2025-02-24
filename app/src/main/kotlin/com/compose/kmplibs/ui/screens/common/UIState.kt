package com.compose.kmplibs.ui.screens.common

sealed interface UIState<out T> {
    class Loading<T> : UIState<T>
    data class Success<T>(val data: T) : UIState<T>
    data class Error<T>(val error: String) : UIState<T>
}