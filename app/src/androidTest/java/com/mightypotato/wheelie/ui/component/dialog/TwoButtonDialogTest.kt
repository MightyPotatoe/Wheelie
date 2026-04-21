package com.mightypotato.wheelie.ui.component.dialog

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented tests for the [TwoButtonDialog] component.
 *
 * Verifies that the dialog renders its content correctly and triggers the appropriate
 * callbacks when the buttons are clicked.
 */
class TwoButtonDialogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Verifies that all elements of the dialog (title, message, buttons) are displayed.
     */
    @Test
    fun dialog_rendersAllContent() {
        composeTestRule.setContent {
            TwoButtonDialog(
                dialogTitle = "Test Title",
                dialogMessage = "Test Message",
                confirmButtonText = "Confirm",
                cancelButtonText = "Cancel",
                onConfirm = {},
                onDismiss = {}
            )
        }

        composeTestRule.onNodeWithText("Test Title").assertExists()
        composeTestRule.onNodeWithText("Test Message").assertExists()
        composeTestRule.onNodeWithText("Confirm").assertExists()
        composeTestRule.onNodeWithText("Cancel").assertExists()
    }

    /**
     * Verifies that clicking the confirm and cancel buttons triggers their respective callbacks.
     */
    @Test
    fun dialog_buttonClicks_triggerCallbacks() {
        var confirmClicked = false
        var dismissClicked = false

        composeTestRule.setContent {
            TwoButtonDialog(
                dialogTitle = "Title",
                dialogMessage = "Msg",
                confirmButtonText = "Confirm",
                cancelButtonText = "Cancel",
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
