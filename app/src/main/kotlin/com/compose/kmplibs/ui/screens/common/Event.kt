package com.compose.kmplibs.ui.screens.common

sealed interface Event {
    data class OnError(val message: String): Event
}