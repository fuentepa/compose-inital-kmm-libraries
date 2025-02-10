package com.compose.kmplibs.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Detail(val moviId: Int)