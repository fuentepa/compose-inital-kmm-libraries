package com.compose.kmplibs.ui.screens.movies

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.compose.kmplibs.BuildConfig
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
    modifier: Modifier = Modifier
) {
    var isClicked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            //.clickable { isClicked = true }
            .padding(8.dp)
    ) {

        Card {
            AsyncImage(
                /*model = ImageRequest.Builder(LocalContext.current)
                    .data("${BuildConfig.TMDB_IMAGE_URL}${movie.posterUrl}")
                    .addHeader("Authorization", "Bearer ${BuildConfig.ACCESS_TOKEN}")
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),*/
                model = "${BuildConfig.TMDB_IMAGE_URL}${movie.posterUrl}",
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .aspectRatio(.5f)
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
                    .padding(8.dp, 16.dp)
                    .weight(1f)
            )
            IconButton(
                onClick = { isClicked = true }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.tooltip_description)
                )
            }
        }
    }

    if (isClicked) {
        onClick(movie.id)
        isClicked = false
    }
}
