package com.compose.kmplibs.ui.screens.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.compose.kmplibs.R

@Composable
fun SnackbarHostState.ShowErrorSnackbar(message: String) {
    if (message.isNotEmpty()) {
        LaunchedEffect(Unit) {
            with(this@ShowErrorSnackbar) {
                showSnackbar(
                    SnackbarVisualsWithError(
                        message = message,
                        isError = true
                    )
                )
            }
        }
    }
}

// Clase personalizada para visuales de Snackbar con error
class SnackbarVisualsWithError(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Long,
    val isError: Boolean = false
) : SnackbarVisuals

// Snackbar Host personalizado
@Composable
fun SnackbarHostState.ErrorSnackbarHost(
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = this,
        modifier = modifier,
    ) { snackbarData ->
        val isError = (snackbarData.visuals as? SnackbarVisualsWithError)?.isError == true
        val backgroundColor = if (isError)
            MaterialTheme.colorScheme.error
        else
            MaterialTheme.colorScheme.inverseSurface

        val contentColor = if (isError)
            MaterialTheme.colorScheme.onError
        else
            MaterialTheme.colorScheme.inverseOnSurface

        Snackbar(
            modifier = Modifier.padding(12.dp),
            action = snackbarData.visuals.actionLabel?.let { actionLabel ->
                {
                    TextButton(
                        modifier = Modifier.semantics {
                            role = Role.Button
                        },
                        onClick = { snackbarData.performAction() },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = contentColor
                        )
                    ) {
                        Text(actionLabel)
                    }
                }
            },
            dismissAction = if (snackbarData.visuals.withDismissAction) {
                {
                    IconButton(
                        onClick = { snackbarData.dismiss() },
                        modifier = Modifier.semantics {
                            role = Role.Button
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.Close),
                            tint = contentColor
                        )
                    }
                }
            } else null,
            containerColor = backgroundColor,
            contentColor = contentColor,
            shape = RoundedCornerShape(8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isError) {
                    Icon(
                        imageVector = Icons.Filled.Warning, // o Icons.Filled.Info
                        contentDescription = "Error",
                        tint = contentColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(snackbarData.visuals.message)
            }
        }
    }
}
