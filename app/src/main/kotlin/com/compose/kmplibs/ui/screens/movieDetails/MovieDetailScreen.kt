package com.compose.kmplibs.ui.screens.movieDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compose.kmplibs.BuildConfig
import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.ui.navigation.AppBarIcon
import com.compose.kmplibs.ui.navigation.TheTopAppBar
import com.compose.kmplibs.ui.screens.common.ErrorSnackbarHost
import com.compose.kmplibs.ui.screens.common.LoadImage
import com.compose.kmplibs.ui.screens.common.LoadingCircularIndicator
import com.compose.kmplibs.ui.screens.common.ShowSnackbar
import com.compose.kmplibs.ui.screens.common.UIState
import com.compose.kmplibs.ui.util.isExpandedScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MovieDetailScreen(
    movieId: Int,
    vm: MovieDetailViewModel = koinViewModel { parametersOf(movieId) },
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val title by rememberSaveable { mutableStateOf("")  }
    val uiState by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TheTopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    AppBarIcon( imageVector = Icons.Default.ArrowBack, onClick = onBack )
                }
            )
        },
        snackbarHost = { snackbarHostState.ErrorSnackbarHost() }
    ) { paddingValues ->
        when (uiState) {
            is UIState.Error -> snackbarHostState.ShowSnackbar((uiState as UIState.Error).error, true)
            is UIState.Loading -> LoadingCircularIndicator()
            is UIState.Success -> {
                (uiState as UIState.Success).data?.let { movieDetail ->
                    MovieDetailsContent(
                        movieDetail,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

@Composable
fun MovieDetailsContent(
    movieDetail: MovieDetail,
    modifier: Modifier = Modifier
) {
    if (isExpandedScreen(currentWindowAdaptiveInfo())) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .weight(0.4f)
                ) {
                    Header(item = movieDetail)
                }

                Box(
                    modifier = Modifier
                        .weight(0.6f)
                ) {
                    Body(item = movieDetail)
                }
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Header(item = movieDetail)
            Spacer(modifier = Modifier.height(16.dp))
            Body(item = movieDetail)
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
                .fillMaxWidth(),
            contentImageDescription = item.posterUrlDescription
        )
    }
}

@Composable
private fun Body(item: MovieDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = item.overview,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp, 0.dp)
        )
        //Spacer(modifier = Modifier.height(16.dp))
    }
}