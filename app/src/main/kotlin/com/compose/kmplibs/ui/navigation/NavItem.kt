package com.compose.kmplibs.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.compose.kmplibs.R


enum class NavItem(
    val icon: ImageVector,
    @StringRes val title: Int
) {
    HOME(Icons.Default.Home, R.string.home),
    SETTINGS(Icons.Default.Settings, R.string.settings)
}
