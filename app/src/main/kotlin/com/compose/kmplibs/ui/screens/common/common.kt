package com.compose.kmplibs.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.compose.kmplibs.R

@Composable
fun LoadingCircularIndicator(
    withText: Boolean = true
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        if (withText) {
            Text(
                text = stringResource(R.string.loading),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun LoadImage(
    url : String,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            // .addHeader("Authorization", "Bearer ${BuildConfig.ACCESS_TOKEN}")
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build(),
        //model = "${BuildConfig.TMDB_IMAGE_URL}${movie.posterUrl}",
        loading = {
            LoadingCircularIndicator(withText = false)
        },
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = modifier
    )
}