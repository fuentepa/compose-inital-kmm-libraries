package com.compose.kmplibs.ui.screens.movieDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.compose.kmplibs.BuildConfig
import com.compose.kmplibs.R
import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.ui.navigation.AppBarIcon
import com.compose.kmplibs.ui.navigation.TheTopAppBar
import com.compose.kmplibs.ui.screens.common.CustomSnackbarHost
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
    var title by rememberSaveable { mutableStateOf("") }
    val uiState by vm.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is UIState.Success) {
            (uiState as UIState.Success).data?.let { movieDetail ->
                title = movieDetail.title
            }
        }
    }

    Scaffold(
        topBar = {
            TheTopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    AppBarIcon( imageVector = Icons.Default.ArrowBack, onClick = onBack )
                }
            )
        },
        snackbarHost = { snackbarHostState.CustomSnackbarHost() }
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
    val isExpanded = isExpandedScreen(currentWindowAdaptiveInfo())

    if (isExpanded) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(0.dp, 0.dp)

        ) {
            Box(
                modifier = Modifier
                    .weight(0.275f)
            ) {
                Header(item = movieDetail, isExpanded)
            }

            Box(
                modifier = Modifier
                    .weight(0.725f)
            ) {
                Body(item = movieDetail)
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Header(item = movieDetail, isExpanded)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_high)))
            Body(item = movieDetail)
        }
    }
}

@Composable
private fun Header(item: MovieDetail, isExpanded: Boolean = false) {
    if (isExpanded) {
        LoadImage(
            url = "${BuildConfig.TMDB_IMAGE_URL}/w500${item.posterUrl}", // el 500 es para forzar un ancho y no traernos algo que pese muchisimo.
            contentImageDescription = item.posterUrlDescription,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(0.7f) //valor para ajustar la proporcion del poster mas correctamente.
                .padding(dimensionResource(R.dimen.screen_padding))
        )
    } else {
        LoadImage(
            url = "${BuildConfig.TMDB_IMAGE_URL}/w1280${item.backdropUrl}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.screen_padding)),
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