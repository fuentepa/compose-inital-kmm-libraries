package com.compose.kmplibs.ui.screens.films

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compose.kmplibs.data.entity.Film
import org.koin.androidx.compose.koinViewModel

@Composable
fun FilmsScreen(
    onClick: @Composable (Film) -> Unit,
    modifier: Modifier = Modifier,
    vm: FilmsViewModel = koinViewModel()
) {
    val state by vm.state.collectAsState()

    if (state.loading)
        Box(modifier = modifier) {
            Text(text = "Loading...")
        }

    if (state.films.isNotEmpty())
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.films) {
                FilmItem(film = it, onClick = onClick)
            }
        }
}

@Composable
fun FilmItem(
    film: Film,
    onClick: @Composable (Film) -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.clickable { isClicked = true }) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            //Image(painter = rememberImagePainter(data = film.imageUrl), contentDescription = null)
            Text(
                text = film.title)
        }
    }

    if (isClicked) {
        onClick(film)
        isClicked = false
    }
}