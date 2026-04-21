package com.mightypotato.wheelie

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for [MainActivity] using Jetpack Compose testing libraries.
 */
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun screenTitle_isDisplayedWithCorrectText() {
        composeTestRule
            .onNodeWithTag("screen_title")
            .assertIsDisplayed()
            .assertTextEquals("Wheels")
    }

    @Test
    fun fabClick_addsNewItem() {
        // Click the FAB which now inserts into the DB
        composeTestRule.onNodeWithTag("add_wheel_button").performClick()
        
        // Verify the snackbar shows the "Added" message
        composeTestRule
            .onNodeWithText("Added", substring = true)
            .assertIsDisplayed()
    }

    @Test
    fun listItemClick_displaysClickMessage() {
        // We look for one of the items we hardcoded in MainActivity
        val itemName = "Koło Fortuny"

        composeTestRule.onNode(
            hasTestTag("list_item") and hasAnyDescendant(hasText(itemName)),
            useUnmergedTree = true
        ).performClick()

        composeTestRule
            .onNodeWithText("Testing onItemClick $itemName")
            .assertIsDisplayed()
    }
}
