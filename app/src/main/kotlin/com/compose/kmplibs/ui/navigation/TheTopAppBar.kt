package com.compose.kmplibs.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import com.compose.kmplibs.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit) = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    val contentTitleDescrption = "${stringResource(R.string.screen_movie_details_title)}: $title"
    TopAppBar(
        title = title,
        modifier = modifier.semantics {
            heading()
            contentDescription = contentTitleDescrption
        },
        navigationIcon = navigationIcon,
        actions = actions
    )
}