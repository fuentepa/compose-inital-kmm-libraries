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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.compose.kmplibs.BuildConfig
import com.compose.kmplibs.R
import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.ui.navigation.TheTopAppBar
import com.compose.kmplibs.ui.screens.common.CustomSnackbarHost
import com.compose.kmplibs.ui.screens.common.LoadImage
import com.compose.kmplibs.ui.screens.common.LoadingCircularIndicator
import com.compose.kmplibs.ui.screens.common.ShowSnackbar
import com.compose.kmplibs.ui.screens.common.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreen(
    vm: MoviesViewModel = koinViewModel(),
    onMovieClick: (Int) -> Unit)
{
   //val appState: AppState = rememberAppState() //por si se usa un Navigation Drawer
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TheTopAppBar( title = { Text(text = stringResource(id = R.string.screen_movies_title)) } )
        },
       snackbarHost = { snackbarHostState.CustomSnackbarHost() }
    ) { paddingValues ->
        when (uiState) {
            is UIState.Error -> snackbarHostState.ShowSnackbar((uiState as UIState.Error).error, true)
            is UIState.Loading -> LoadingCircularIndicator()
            is UIState.Success ->
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(160.dp),
                    modifier = Modifier.padding(paddingValues),
                    contentPadding = PaddingValues(dimensionResource(R.dimen.item_padding))
                ) {
                    val movies = (uiState as UIState.Success<List<Movie>>).data
                    items(movies, key = { it.id }) {
                        MovieItem(movie = it, onClickMovie = { onMovieClick(it.id) })
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
            .minimumInteractiveComponentSize()
            .clickable(
                onClickLabel = stringResource(R.string.action_movie_details),
                onClick = onClickMovie )
            .padding(4.dp)
            .semantics {
                contentDescription = movie.title
            }
    ) {
        Card {
            LoadImage(
                url = "${BuildConfig.TMDB_IMAGE_URL}/w500${movie.posterUrl}", // el 500 es para forzar un ancho y no traernos algo que pese muchisimo.
                contentImageDescription = movie.posterUrlDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.675f) //valor para ajustar la proporcion del poster mas correctamente.
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
                    .padding(4.dp, dimensionResource(R.dimen.item_padding_half))
                    .weight(1f)
            )
            IconButton(
                modifier = Modifier.minimumInteractiveComponentSize(),
                onClick = onClickMovie) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
        }
    }
}
