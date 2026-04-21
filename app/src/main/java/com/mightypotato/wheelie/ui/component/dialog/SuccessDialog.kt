package com.mightypotato.wheelie.ui.component.dialog

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mightypotato.wheelie.ui.theme.AppTheme

/**
 * A success dialog component that displays a title, a success icon, 
 * a message, and a confirm button.
 *
 * @param title The text to display as the dialog's title.
 * @param message The descriptive text shown in the body of the dialog.
 * @param confirmButtonText The label for the confirmation button.
 * @param onConfirm Callback invoked when the confirm button is clicked.
 * @param onDismiss Callback invoked when the dialog is dismissed. Defaults to [onConfirm].
 */
@Composable
fun SuccessDialog(
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
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF4CAF50), // Success Green
                modifier = Modifier.size(48.dp)
            )
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = confirmButtonText)
            }
        }
    )
}

/**
 * Preview for [SuccessDialog].
 */
@Preview(showBackground = true)
@Composable
fun SuccessDialogPreview() {
    AppTheme {
        SuccessDialog(
            title = "Success",
            message = "Operation completed successfully!",
            confirmButtonText = "OK",
            onConfirm = {}
        )
    }
}
