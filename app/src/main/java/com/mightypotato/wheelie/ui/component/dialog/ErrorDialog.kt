package com.mightypotato.wheelie.ui.component.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mightypotato.wheelie.ui.theme.AppTheme

/**
 * A reusable error dialog component that displays an error icon, title, centered message,
 * and a confirm button.
 *
 * This dialog is designed to communicate error states to the user. It uses the
 * theme's error color for the icon and centers both the title and the message text.
 *
 * @param title The text to display as the dialog's title.
 * @param message The detailed error message to display in the body of the dialog.
 * @param confirmButtonText The text for the positive action button (e.g., "OK").
 * @param onConfirm Callback invoked when the confirm button is clicked.
 * @param onDismiss Callback invoked when the dialog is dismissed. Defaults to [onConfirm].
 */
@Composable
fun ErrorDialog(
    title: String,
    message: String,
    confirmButtonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit = onConfirm,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(48.dp)
            )
        },
        title = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = message,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = confirmButtonText)
            }
        }
    )
}

/**
 * Preview for [ErrorDialog].
 */
@Preview(showBackground = true)
@Composable
fun ErrorDialogPreview() {
    AppTheme {
        ErrorDialog(
            title = "Error",
            message = "An error occurred!",
            confirmButtonText = "OK",
            onConfirm = {}
        )
    }
}
