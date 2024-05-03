package com.compose.kmplibs.ui.screens.movieDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.compose.kmplibs.R
import com.compose.kmplibs.ui.navigation.AppBarIcon
import com.compose.kmplibs.ui.navigation.TheTopAppBar
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

    if (state.loading)
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading...")
        }

    state.movieDetails?.let {
        Text(
            text = it.overview,
            modifier = modifier
        )
    }

}
