package com.compose.kmplibs.ui.screens.films

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.compose.kmplibs.data.entity.Film
import org.koin.androidx.compose.koinViewModel

@Composable
fun FilmsScreen(
    onClick: @Composable (Film) -> Unit,
    modifier: Modifier = Modifier,
    vm: FilmsViewModel = koinViewModel()
) {
    val state by vm.state.collectAsState()

    state.films.forEach { film ->
        val scope = rememberCoroutineScope()

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = film.title)
        }
    }

}