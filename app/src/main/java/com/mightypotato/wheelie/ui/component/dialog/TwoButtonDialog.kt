package com.mightypotato.wheelie.ui.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mightypotato.wheelie.ui.theme.AppTheme

/**
 * A reusable dialog component that displays a title, a message,
 * and two buttons (confirm and cancel).
 *
 * @param dialogTitle The text to display as the dialog's title.
 * @param dialogMessage The message text shown in the body of the dialog.
 * @param confirmButtonText The label for the positive action button.
 * @param cancelButtonText The label for the negative action button.
 * @param onConfirm Callback invoked when the confirm button is clicked.
 * @param onDismiss Callback invoked when the dialog is dismissed or the cancel button is clicked.
 */
@Composable
fun TwoButtonDialog(
    dialogTitle: String,
    dialogMessage: String,
    confirmButtonText: String,
    cancelButtonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = dialogTitle) },
        text = {
            Column {
                Text(text = dialogMessage)
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
            ) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(cancelButtonText)
            }
        }
    )
}

/**
 * Preview for [TwoButtonDialog] showing a typical configuration.
 */
@Preview(showBackground = true)
@Composable
fun TwoButtonDialogPreview() {
    AppTheme {
        TwoButtonDialog(
            dialogTitle = "Delete Item",
            dialogMessage = "Are you sure you want to delete this item?",
            confirmButtonText = "Delete",
            cancelButtonText = "Cancel",
            onConfirm = {},
            onDismiss = {}
        )
    }
}
