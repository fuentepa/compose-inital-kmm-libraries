package com.compose.kmplibs.ui.screens.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.ui.screens.movieDetails.MovieDetailScreen
import org.koin.androidx.compose.koinViewModel

class MoviesScreen: Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val onClick: @Composable (Int) -> Unit = {
            navigator.push(MovieDetailScreen(movieId = it))
        }

        MoviesScreen(onClick)
    }
}

@Composable
fun MoviesScreen(
    onClick: @Composable (Int) -> Unit,
    modifier: Modifier = Modifier,
    vm: MoviesViewModel = koinViewModel(),
) {
    val state by vm.state.collectAsState()

    if (state.loading)
        Box(modifier = modifier) {
            Text(text = "Loading...")
        }

    if (state.movies.isNotEmpty())
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.movies) {
                MovieItem(movie = it, onClick = onClick)
            }
        }
}

@Composable
fun MovieItem(
    movie: Movie,
    onClick: @Composable (Int) -> Unit,
) {
    var isClicked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.clickable { isClicked = true }) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            //TODO:
            // Image(painter = rememberImagePainter(data = film.imageUrl), contentDescription = null)
            Text(text = movie.title)
        }
    }

    if (isClicked) {
        onClick(movie.id)
        isClicked = false
    }
}
