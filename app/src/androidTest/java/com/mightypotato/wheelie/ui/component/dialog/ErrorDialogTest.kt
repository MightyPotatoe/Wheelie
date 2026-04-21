package com.mightypotato.wheelie.ui.component.dialog

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented tests for the [ErrorDialog] component.
 */
class ErrorDialogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Verifies that the dialog renders its title, message, and button text correctly.
     */
    @Test
    fun errorDialog_rendersContent() {
        val title = "Error Title"
        val message = "Error Message"
        val buttonText = "OK"

        composeTestRule.setContent {
            ErrorDialog(
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
    fun errorDialog_onConfirmClicked_triggersCallback() {
        var confirmClicked = false
        val buttonText = "OK"

        composeTestRule.setContent {
            ErrorDialog(
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
