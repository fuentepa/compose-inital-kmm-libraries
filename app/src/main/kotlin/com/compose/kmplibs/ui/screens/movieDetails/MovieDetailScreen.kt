package com.compose.kmplibs.ui.screens.movieDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

data class MovieDetailScreen(
    val movieId: Int
): Screen {
    @Composable
    override fun Content() {
        DetailMovieScreen(movieId = movieId)
    }
}

@Composable
fun DetailMovieScreen(
    movieId: Int,
    modifier: Modifier = Modifier,
    vm: MovieDetailViewModel = koinViewModel { parametersOf(movieId) }
) {
    val state by vm.state.collectAsState()

    if (state.loading)
        Box(modifier = modifier) {
            Text(text = "Loading...")
        }

    state.movieDetails?.let {
        Text(text = it.overview)
    }
}
