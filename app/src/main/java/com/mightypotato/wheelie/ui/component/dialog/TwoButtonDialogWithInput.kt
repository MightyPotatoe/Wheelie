package com.mightypotato.wheelie.ui.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mightypotato.wheelie.ui.theme.AppTheme

/**
 * A reusable dialog component that displays a title, a message, an input field,
 * and two buttons (confirm and cancel).
 *
 * @param dialogTitle The text to display as the dialog's title.
 * @param dialogMessage A prompt or descriptive text shown above the input field.
 * @param inputLabel The label for the [OutlinedTextField].
 * @param inputValue The current value of the input field, typically held in a state.
 * @param confirmButtonText The label for the positive action button.
 * @param cancelButtonText The label for the negative action button.
 * @param onNameChange Callback invoked when the input text is modified.
 * @param onConfirm Callback invoked when the confirm button is clicked.
 * @param onDismiss Callback invoked when the dialog is dismissed or the cancel button is clicked.
 */
@Composable
fun TwoButtonDialogWithInput(
    dialogTitle: String,
    dialogMessage: String,
    inputLabel: String,
    inputValue: String,
    confirmButtonText: String,
    cancelButtonText: String,
    onNameChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = dialogTitle) },
        text = {
            Column {
                Text(text = dialogMessage)
                OutlinedTextField(
                    value = inputValue,
                    onValueChange = onNameChange,
                    modifier = Modifier.padding(top = 8.dp),
                    label = { Text(inputLabel) },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = inputValue.isNotBlank()
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
 * Preview for [TwoButtonDialogWithInput] showing a typical configuration.
 */
@Preview(showBackground = true)
@Composable
fun TwoButtonDialogWithInputPreview() {
    AppTheme {
        TwoButtonDialogWithInput(
            dialogTitle = "Add Item",
            dialogMessage = "Please enter the name of the new item:",
            inputLabel = "Item Name",
            inputValue = "Sample Item",
            confirmButtonText = "Add",
            cancelButtonText = "Cancel",
            onNameChange = {},
            onConfirm = {},
            onDismiss = {}
        )
    }
}
