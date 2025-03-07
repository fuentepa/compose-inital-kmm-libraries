package com.compose.kmplibs.ui.util

import android.util.Log
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.window.core.layout.WindowWidthSizeClass.Companion.EXPANDED

//aqui se pueden cambiar los valores a lo que nos pidan en el proyecto
fun getNavigationTypeForWindowInfo(windowInfo: WindowAdaptiveInfo): NavigationSuiteType {
    Log.d("getNavigationTypeForWindowInfo", "with = ${windowInfo.windowSizeClass.windowWidthSizeClass} and height = ${windowInfo.windowSizeClass.windowHeightSizeClass}")
    return with(windowInfo) {
        if (
            windowPosture.isTabletop ||
            windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.COMPACT
        ) {
            NavigationSuiteType.NavigationRail
        } else if (
            windowSizeClass.windowWidthSizeClass == EXPANDED ||
            windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM
        ) {
            NavigationSuiteType.NavigationRail
        } else {
            NavigationSuiteType.NavigationBar
        }
    }
}

fun isExpandedScreen(windowInfo: WindowAdaptiveInfo): Boolean =
    windowInfo.windowSizeClass.windowWidthSizeClass == EXPANDED