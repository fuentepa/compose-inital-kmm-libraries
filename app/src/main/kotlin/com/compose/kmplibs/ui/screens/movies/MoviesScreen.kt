package com.compose.kmplibs.ui.screens.movies


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorContent
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.compose.kmplibs.BuildConfig
import com.compose.kmplibs.R
import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.ui.AppState
import com.compose.kmplibs.ui.navigation.AppBarIcon
import com.compose.kmplibs.ui.navigation.DrawerContent
import com.compose.kmplibs.ui.navigation.TheTopAppBar
import com.compose.kmplibs.ui.rememberAppState
import com.compose.kmplibs.ui.screens.common.LoadingIndicator
import com.compose.kmplibs.ui.screens.movieDetails.MovieDetailScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class MoviesScreen: Screen {

    @Composable
    override fun Content() {
        val appState: AppState = rememberAppState()
        val navigator = LocalNavigator.currentOrThrow

        val onClick: @Composable (Int) -> Unit = {
            navigator.push(MovieDetailScreen(movieId = it))
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
            }
        ) { paddingValues ->
            ListMoviesScreen(
                onClick = onClick,
                modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun ListMoviesScreen(
    onClick: @Composable (Int) -> Unit,
    modifier: Modifier = Modifier,
    vm: MoviesViewModel = koinViewModel(),
) {
    val state by vm.state.collectAsState()

    if (state.loading)
        LoadingIndicator()

    if (state.movies.isNotEmpty())
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(8.dp)
        )  {
            items(state.movies) {
                MovieItem(movie = it, onClick = onClick)
            }
        }

    state.error?.let {
        //Deberia recibir un onError para mostrar en el Scaffold.snackbarhost, como un snackbar
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
            AsyncImage(
                /*model = ImageRequest.Builder(LocalContext.current)
                    .data("${BuildConfig.TMDB_IMAGE_URL}${movie.posterUrl}")
                    .addHeader("Authorization", "Bearer ${BuildConfig.ACCESS_TOKEN}")
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),*/
                model = "${BuildConfig.TMDB_IMAGE_URL}${movie.posterUrl}",
                contentDescription = movie.title,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .aspectRatio(0.675f)
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
