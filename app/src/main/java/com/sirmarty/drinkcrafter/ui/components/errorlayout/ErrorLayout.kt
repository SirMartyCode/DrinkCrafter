package com.sirmarty.drinkcrafter.ui.components.errorlayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sirmarty.drinkcrafter.R


@Composable
fun ErrorLayout(
    throwable: Throwable,
    showErrorDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    val errorMessage = throwable.message

    Box(modifier = Modifier.fillMaxSize()) {
        if (showErrorDialog) {
            ErrorDialog(
                title = "Error",
                text = errorMessage,
                onDismiss = onDismissRequest,
                onConfirmation = onConfirmation
            )
        }
        RetryButton(
            modifier = Modifier.align(Alignment.Center),
            onClick = onConfirmation
        )
    }
}

//==============================================================================================
//region Private composable

@Composable
private fun ErrorDialog(
    title: String,
    text: String?,
    onDismiss: () -> Unit,
    onConfirmation: () -> Unit,
) {
    val context = LocalContext.current

    AlertDialog(
        icon = {
            Icon(
                painterResource(R.drawable.ic_alert),
                contentDescription = "Example Icon"
            )
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(
                text = text ?: context.getString(R.string.error_dialog_default_message),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = context.getString(R.string.error_dialog_try_again))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = context.getString(R.string.error_dialog_cancel),
                    color = Color.DarkGray
                )
            }
        }
    )
}

@Composable
private fun RetryButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape),
        onClick = onClick,
        colors = IconButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
            // It should never be disabled
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified
        )
    ) {
        Icon(
            imageVector = Icons.Outlined.Refresh,
            modifier = Modifier.size(40.dp),
            contentDescription = ""
        )
    }
}

//endregion
//==============================================================================================
//region Preview

@Preview
@Composable
private fun ErrorDialogPreview() {
    ErrorDialog(
        title = "Error title",
        text = null,
        onDismiss = {},
        onConfirmation = {}
    )
}

@Preview
@Composable
private fun RetryButtonPreview() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        RetryButton(
            modifier = Modifier.align(Alignment.Center),
            onClick = {}
        )
    }
}

//endregion