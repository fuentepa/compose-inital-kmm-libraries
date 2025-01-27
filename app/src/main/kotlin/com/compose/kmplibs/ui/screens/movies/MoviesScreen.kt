package com.compose.kmplibs.ui.screens.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.compose.kmplibs.BuildConfig
import com.compose.kmplibs.R
import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.ui.AppState
import com.compose.kmplibs.ui.navigation.AppBarIcon
import com.compose.kmplibs.ui.navigation.DrawerContent
import com.compose.kmplibs.ui.navigation.TheTopAppBar
import com.compose.kmplibs.ui.rememberAppState
import com.compose.kmplibs.ui.screens.common.LoadImage
import com.compose.kmplibs.ui.screens.common.LoadingCircularIndicator
import com.compose.kmplibs.ui.screens.common.UIState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreen(navController: NavController) {
    val appState: AppState = rememberAppState()

    val onClick: @Composable (Int) -> Unit = { movieId ->
        navController.navigate("movieDetail/$movieId")
    }

    ModalNavigationDrawer(
        drawerState = appState.drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(250.dp)
            ) {
                DrawerContent(
                    drawerOptions = AppState.DRAWER_OPTIONS,
                    selectedIndex = 0,
                    onOptionClick = { appState.coroutineScope.launch { appState.drawerState.close() } }
                )
            }
        }
    ) {

        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            topBar = {
                TheTopAppBar(
                    title = { Text(text = stringResource(id = R.string.screen_movies_title)) },
                    navigationIcon = {
                        AppBarIcon(
                            imageVector = Icons.Default.Menu,
                            onClick = { appState.onMenuClick() }
                        )
                    }
                )
            },
           snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
        ) { paddingValues ->
            ListMoviesScreen(
                onClick = onClick,
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
    }

@Composable
fun ListMoviesScreen(
    onClick: @Composable (Int) -> Unit,
    modifier: Modifier = Modifier,
    onErrorAction: @Composable (String) -> Unit = {},
    vm: MoviesViewModel = koinViewModel(),
) {
    val state by vm.state.collectAsState()

    when (state) {
        is UIState.Error -> onErrorAction((state as UIState.Error).error)
        is UIState.Loading -> LoadingCircularIndicator()
        is UIState.Success -> {
            (state as UIState.Success<List<Movie>>).data.let { movies ->
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(160.dp),
                    modifier = modifier,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(movies, key = { it.id }) {
                        MovieItem(movie = it, onClick = onClick)
                    }
                }
            }
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
            .clickable { isClicked = true }
            .padding(4.dp)
    ) {
        Card {
            LoadImage(
                url = "${BuildConfig.TMDB_IMAGE_URL}/w500${movie.posterUrl}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.675f)
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
            IconButton(
                onClick = { isClicked = true }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
        }
    }

    if (isClicked) {
        onClick(movie.id)
        isClicked = false
    }
}
