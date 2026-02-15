package com.compose.kmplibs.ui.screens.common

import android.util.Log
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
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.compose.kmplibs.R

@Composable
fun LoadingCircularIndicator(
    modifier: Modifier = Modifier,
    withText: Boolean = true,
    contentLoadingDescription: String? = null,
) {
    val loadingText = stringResource(R.string.loading)
    val accessibilityDescription =
        contentLoadingDescription?.let { "$loadingText: $contentLoadingDescription" } ?: loadingText

    Column(
        modifier = modifier
            .fillMaxSize()
            .semantics {
                liveRegion = LiveRegionMode.Polite
                contentDescription = accessibilityDescription
                isTraversalGroup = true
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.semantics {
                contentDescription = ""
            }
        )
        if (withText) {
            Text(
                text = stringResource(R.string.loading),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .semantics {
                        contentDescription = ""
                    }
            )
        }
    }
}

@Composable
fun LoadImage(
    url: String,
    modifier: Modifier = Modifier,
    contentImageDescription: String? = null,
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build(),

      //  model = url,
        loading = {
            LoadingCircularIndicator(withText = false)
        },
        contentDescription = contentImageDescription,
        contentScale = ContentScale.FillWidth,
        modifier = modifier.semantics {
            role = Role.Image
        },
        onError = { Log.e("LoadImage", "Coil error = " + it.result.throwable.message.toString()) },
        onLoading = { Log.d("LoadImage", "Coil calling to = $url") }
    )
}