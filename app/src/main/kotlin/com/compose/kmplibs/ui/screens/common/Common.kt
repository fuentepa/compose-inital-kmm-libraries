package com.compose.kmplibs.ui.screens.common

import android.content.Context
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.compose.kmplibs.R

@Composable
fun LoadingCircularIndicator(
    withText: Boolean = true,
    contentLoadingDescription: String? = null,
) {
    val loadingText = stringResource(R.string.loading)
    val accessibilityDescription = when {
        contentLoadingDescription != null -> "$loadingText: $contentLoadingDescription"
        else -> loadingText
    }
    val ctx = LocalContext.current
    DisposableEffect(Unit) {
        val accessibilityManager =
            ctx.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager
        if (accessibilityManager?.isEnabled == true) {
            val event = AccessibilityEvent.obtain(AccessibilityEvent.TYPE_ANNOUNCEMENT)
            event.text.add(accessibilityDescription)
            accessibilityManager.sendAccessibilityEvent(event)
        }
        onDispose { }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .semantics {
                liveRegion = LiveRegionMode.Polite
                contentLoadingDescription?.let { contentDescription = accessibilityDescription }
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
        model = url,
        loading = {
            LoadingCircularIndicator(withText = false)
        },
        contentDescription = contentImageDescription,
        contentScale = ContentScale.FillWidth,
        modifier = modifier.semantics {
            role = Role.Image
        }
    )
}