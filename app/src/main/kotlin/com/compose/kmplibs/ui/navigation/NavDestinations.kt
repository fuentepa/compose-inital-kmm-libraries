package com.compose.kmplibs.ui.navigation

import kotlinx.serialization.Serializable

sealed interface NavDestinations {
    @Serializable
    data object Home: NavDestinations

    @Serializable
    data class Detail(val moviId: Int): NavDestinations

    @Serializable
    data object Settings: NavDestinations
}