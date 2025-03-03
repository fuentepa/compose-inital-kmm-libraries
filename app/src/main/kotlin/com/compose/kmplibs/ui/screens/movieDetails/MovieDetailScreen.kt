package com.compose.kmplibs.ui.screens.movieDetails

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.compose.kmplibs.BuildConfig
import com.compose.kmplibs.R
import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.ui.navigation.AppBarIcon
import com.compose.kmplibs.ui.navigation.TheTopAppBar
import com.compose.kmplibs.ui.screens.common.LoadImage
import com.compose.kmplibs.ui.screens.common.LoadingCircularIndicator
import com.compose.kmplibs.ui.screens.common.UIState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MovieDetailScreen(
    movieId: Int,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TheTopAppBar(
                title = { Text(text = stringResource(id = R.string.screen_movie_details_title)) },
                navigationIcon = {
                    AppBarIcon(
                        imageVector = Icons.Default.ArrowBack,
                        onClick = { onBack() }
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        DetailMovieScreen(
            movieId = movieId,
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
fun DetailMovieScreen(
    movieId: Int,
    modifier: Modifier = Modifier,
    onErrorAction: @Composable (String) -> Unit = {},
    vm: MovieDetailViewModel = koinViewModel { parametersOf(movieId) }
) {
    val state by vm.state.collectAsState()

    Log.d("TAG", "-> DetailMovieScreen: state = $state")

    when (state) {
        is UIState.Error -> onErrorAction((state as UIState.Error).error)
        is UIState.Loading -> LoadingCircularIndicator()
        is UIState.Success -> {
            (state as UIState.Success).data?.let { movieDetail ->
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Header(item = movieDetail)
                }
            }
        }
    }
}

@Composable
private fun Header(item: MovieDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LoadImage(
            url = "${BuildConfig.TMDB_IMAGE_URL}/w1280${item.backdropUrl}",
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = item.title
                }
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