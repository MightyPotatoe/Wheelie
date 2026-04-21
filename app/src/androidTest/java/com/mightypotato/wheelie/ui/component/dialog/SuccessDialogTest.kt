package com.mightypotato.wheelie.ui.component.dialog

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented tests for the [SuccessDialog] component.
 */
class SuccessDialogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Verifies that the dialog displays the correct title, message, and button text.
     */
    @Test
    fun successDialog_rendersCorrectContent() {
        val title = "Success!"
        val message = "Operation completed."
        val buttonText = "Got it"

        composeTestRule.setContent {
            SuccessDialog(
                title = title,
                message = message,
                confirmButtonText = buttonText,
                onConfirm = {}
            )
        }

        composeTestRule.onNodeWithText(title).assertExists()
        composeTestRule.onNodeWithText(message).assertExists()
        composeTestRule.onNodeWithText(buttonText).assertExists()
    }

    /**
     * Verifies that clicking the confirm button triggers the onConfirm callback.
     */
    @Test
    fun successDialog_confirmButtonClick_triggersCallback() {
        var confirmClicked = false
        val buttonText = "Confirm"

        composeTestRule.setContent {
            SuccessDialog(
                title = "Title",
                message = "Message",
                confirmButtonText = buttonText,
                onConfirm = { confirmClicked = true }
            )
        }

        composeTestRule.onNodeWithText(buttonText).performClick()
        assertTrue(confirmClicked)
    }
}
