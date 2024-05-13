package com.compose.kmplibs.ui.screens.movieDetails

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.compose.kmplibs.BuildConfig
import com.compose.kmplibs.R
import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.ui.navigation.AppBarIcon
import com.compose.kmplibs.ui.navigation.TheTopAppBar
import com.compose.kmplibs.ui.screens.common.LoadImage
import com.compose.kmplibs.ui.screens.common.LoadingIndicator
import com.compose.kmplibs.ui.screens.common.UIState
import com.compose.kmplibs.ui.screens.movies.ListMoviesScreen
import com.compose.kmplibs.ui.screens.movies.MoviesScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

data class MovieDetailScreen(
    val movieId: Int
): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                TheTopAppBar(
                    title = { Text(text = stringResource(id = R.string.screen_movie_details_title)) },
                    navigationIcon = {
                        AppBarIcon(
                            imageVector = Icons.Default.ArrowBack,
                            onClick = { navigator.pop() }
                        )
                    }
                )
            }
        ) { paddingValues ->
            DetailMovieScreen(
                movieId = movieId,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun DetailMovieScreen(
    movieId: Int,
    modifier: Modifier = Modifier,
    vm: MovieDetailViewModel = koinViewModel { parametersOf(movieId) }
) {
    val state by vm.state.collectAsState()

    when (state) {
        is UIState.Loading -> LoadingIndicator()
        is UIState.Error -> TODO( "Show error")
        is UIState.Success -> {
            (state as UIState.Success<MovieDetail>).data.let {
                LazyColumn(
                    modifier = modifier
                ) {
                    item {
                        Header(item = it)
                    }
                    /*item.references.forEach {
                        val (icon, @StringRes stringRes) = it.type.createUiData()
                        section(icon, stringRes, it.references)
                    }*/
                }
            }

        }
    }
}

@Composable
private fun Header(item: MovieDetail) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LoadImage(
            url = "${BuildConfig.TMDB_IMAGE_URL}/w1280${item.backdropUrl}",
            modifier = Modifier
                .fillMaxWidth()
            //.aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = item.title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = item.overview,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp, 0.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}