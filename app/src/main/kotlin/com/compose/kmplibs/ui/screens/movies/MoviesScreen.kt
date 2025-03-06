package com.compose.kmplibs.ui.screens.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.compose.kmplibs.BuildConfig
import com.compose.kmplibs.R
import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.ui.AppState
import com.compose.kmplibs.ui.navigation.TheTopAppBar
import com.compose.kmplibs.ui.rememberAppState
import com.compose.kmplibs.ui.screens.common.LoadImage
import com.compose.kmplibs.ui.screens.common.LoadingCircularIndicator
import com.compose.kmplibs.ui.screens.common.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreen(onMovieClick: (Int) -> Unit) {
    val appState: AppState = rememberAppState()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TheTopAppBar( title = { Text(text = stringResource(id = R.string.screen_movies_title)) } )
        },
       snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ) { paddingValues ->
        MoviesContent(
            onClickItem = onMovieClick,
            onErrorAction = {
                LaunchedEffect(Unit) {
                    with(snackbarHostState) {
                        currentSnackbarData?.dismiss()
                        showSnackbar(it)
                    }
                }
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}



@Composable
fun MoviesContent(
    onClickItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onErrorAction: @Composable (String) -> Unit = {},
    vm: MoviesViewModel = koinViewModel(),
) {

    val uiState by vm.uiState.collectAsState()

    when (uiState) {
        is UIState.Error -> onErrorAction((uiState as UIState.Error).error)
        is UIState.Loading -> LoadingCircularIndicator()
        is UIState.Success -> {
            (uiState as UIState.Success<List<Movie>>).data.let { movies ->
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(160.dp),
                    modifier = modifier,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(movies, key = { it.id }) {
                        MovieItem(movie = it, onClickMovie = { onClickItem(it.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onClickMovie:  () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClickMovie )
            .padding(4.dp)
    ) {
        Card {
            LoadImage(
                url = "${BuildConfig.TMDB_IMAGE_URL}/w500${movie.posterUrl}", // el 500 es para forzar un ancho y no traernos algo que pese muchisimo.
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.675f) //valor para ajustar la proporcion del poster mas correctamente.
                    .semantics {
                        contentDescription = movie.title
                    }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                modifier = Modifier
                    .padding(4.dp, 8.dp)
                    .weight(1f)
            )
            IconButton(onClick = onClickMovie) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
        }
    }
}
