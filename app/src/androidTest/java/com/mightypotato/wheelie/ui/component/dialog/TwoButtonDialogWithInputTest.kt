package com.mightypotato.wheelie.ui.component.dialog

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented tests for the [TwoButtonDialogWithInput] component.
 *
 * Verifies that the dialog renders its content correctly, handles input changes,
 * and manages button enablement based on the input state.
 */
class TwoButtonDialogWithInputTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Verifies that all elements of the dialog (title, message, label, buttons) are displayed.
     */
    @Test
    fun dialog_rendersAllContent() {
        composeTestRule.setContent {
            TwoButtonDialogWithInput(
                dialogTitle = "Test Title",
                dialogMessage = "Test Message",
                inputLabel = "Test Label",
                inputValue = "Initial",
                confirmButtonText = "Confirm",
                cancelButtonText = "Cancel",
                onNameChange = {},
                onConfirm = {},
                onDismiss = {}
            )
        }

        composeTestRule.onNodeWithText("Test Title").assertExists()
        composeTestRule.onNodeWithText("Test Message").assertExists()
        composeTestRule.onNodeWithText("Test Label").assertExists()
        composeTestRule.onNodeWithText("Confirm").assertExists()
        composeTestRule.onNodeWithText("Cancel").assertExists()
        composeTestRule.onNodeWithText("Initial").assertExists()
    }

    /**
     * Verifies that the confirm button is disabled when the input is blank and enabled otherwise.
     */
    @Test
    fun dialog_confirmButton_enablementLogic() {
        // Test disabled state
        composeTestRule.setContent {
            TwoButtonDialogWithInput(
                dialogTitle = "Title",
                dialogMessage = "Msg",
                inputLabel = "Label",
                inputValue = "  ", // Blank
                confirmButtonText = "Confirm",
                cancelButtonText = "Cancel",
                onNameChange = {},
                onConfirm = {},
                onDismiss = {}
            )
        }
        composeTestRule.onNodeWithText("Confirm").assertIsNotEnabled()

        // Test enabled state
        composeTestRule.setContent {
            TwoButtonDialogWithInput(
                dialogTitle = "Title",
                dialogMessage = "Msg",
                inputLabel = "Label",
                inputValue = "Valid Text",
                confirmButtonText = "Confirm",
                cancelButtonText = "Cancel",
                onNameChange = {},
                onConfirm = {},
                onDismiss = {}
            )
        }
        composeTestRule.onNodeWithText("Confirm").assertIsEnabled()
    }

    /**
     * Verifies that typing in the input field triggers the [onNameChange] callback.
     */
    @Test
    fun dialog_inputChange_triggersCallback() {
        var capturedValue = ""
        val label = "Input Label"

        composeTestRule.setContent {
            TwoButtonDialogWithInput(
                dialogTitle = "Title",
                dialogMessage = "Msg",
                inputLabel = label,
                inputValue = "",
                confirmButtonText = "Confirm",
                cancelButtonText = "Cancel",
                onNameChange = { capturedValue = it },
                onConfirm = {},
                onDismiss = {}
            )
        }

        composeTestRule.onNodeWithText(label).performTextInput("New Name")
        assertEquals("New Name", capturedValue)
    }

    /**
     * Verifies that clicking the confirm and cancel buttons triggers their respective callbacks.
     */
    @Test
    fun dialog_buttonClicks_triggerCallbacks() {
        var confirmClicked = false
        var dismissClicked = false

        composeTestRule.setContent {
            TwoButtonDialogWithInput(
                dialogTitle = "Title",
                dialogMessage = "Msg",
                inputLabel = "Label",
                inputValue = "Something",
                confirmButtonText = "Confirm",
                cancelButtonText = "Cancel",
                onNameChange = {},
                onConfirm = { confirmClicked = true },
                onDismiss = { dismissClicked = true }
            )
        }

        composeTestRule.onNodeWithText("Confirm").performClick()
        assertTrue(confirmClicked)

        composeTestRule.onNodeWithText("Cancel").performClick()
        assertTrue(dismissClicked)
    }
}
