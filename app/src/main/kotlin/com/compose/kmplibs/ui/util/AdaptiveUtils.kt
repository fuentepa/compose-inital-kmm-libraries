package com.compose.kmplibs.ui.util

import android.util.Log
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_MEDIUM_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

//aqui se pueden cambiar los valores a lo que nos pidan en el proyecto
fun getNavigationTypeForWindowInfo(windowInfo: WindowAdaptiveInfo): NavigationSuiteType {
    Log.d(
        "getNavigationTypeForWindowInfo",
        "with = ${windowInfo.windowSizeClass.windowWidthSizeClass} and height = ${windowInfo.windowSizeClass.windowHeightSizeClass}"
    )

    return with(windowInfo) {
        if (windowPosture.isTabletop || isCompactScreen)
            NavigationSuiteType.NavigationRail
        else if (isExpandedScreen || isMediumScreen)
            NavigationSuiteType.NavigationRail
        else
            NavigationSuiteType.NavigationBar
    }
}

val WindowAdaptiveInfo.isExpandedScreen: Boolean
    get() = windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)

val WindowAdaptiveInfo.isMediumScreen: Boolean
    get() = windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)

val WindowAdaptiveInfo.isCompactScreen: Boolean
    get() = !windowSizeClass.isHeightAtLeastBreakpoint(HEIGHT_DP_MEDIUM_LOWER_BOUND)