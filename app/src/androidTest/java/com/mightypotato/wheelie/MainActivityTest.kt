package com.mightypotato.wheelie

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mightypotato.wheelie.extension.fetchTextContents
import org.junit.Rule
import org.junit.Test


/**
 * UI tests for [MainActivity] using Jetpack Compose testing libraries.
 *
 * These tests verify the initial state of the UI, the population of the wheel list,
 * and the interaction with various UI elements such as the Floating Action Button
 * and list items.
 */
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Verifies that the screen title is displayed correctly with the expected text.
     */
    @Test
    fun screenTitle_isDisplayedWithCorrectText() {
        composeTestRule
            .onNodeWithTag("screen_title")
            .assertIsDisplayed()
            .assertTextEquals("Wheels")
    }

    /**
     * Verifies that the list displays the correct number of items and that
     * the text content matches the source data.
     */
    @Test
    fun wheelList_displaysCorrectItems() {
        val nodes = composeTestRule.onAllNodesWithTag("list_item_text", useUnmergedTree = true)

        // Check for the correct number of nodes
        nodes.assertCountEquals(6)

        // Check for the correct text content
        val actualTexts: List<String> = nodes.fetchTextContents()
        assert(actualTexts == MainActivity.wheels) {
            "Expected $ MainActivity.wheels but found $actualTexts"
        }
    }

    /**
     * Verifies that clicking the Floating Action Button triggers the "Not implemented" toast/message.
     */
    @Test
    fun fabClick_displaysNotImplementedMessage() {
        composeTestRule.onNodeWithTag("add_wheel_button").performClick()
        composeTestRule
            .onNodeWithText("Not implemented yet.")
            .assertIsDisplayed()
    }

    /**
     * Verifies that clicking the delete button on a specific list item triggers
     * the appropriate "Not implemented" message containing the item's name.
     */
    @Test
    fun deleteItemClick_displaysItemSpecificDeleteMessage() {
        val itemName = MainActivity.wheels[0]

        composeTestRule.onNode(
            hasTestTag("list_item_delete_button") and
                    hasAnyAncestor(
                        hasTestTag("list_item") and hasAnyDescendant(hasText(itemName))
                    ),
            useUnmergedTree = true
        ).performClick()

        composeTestRule
            .onNodeWithText("Not implemented on delete: $itemName")
            .assertIsDisplayed()
    }

    /**
     * Verifies that clicking a list item row triggers the appropriate
     * "Not implemented" message containing the item's name.
     */
    @Test
    fun listItemClick_displaysItemSpecificClickMessage() {
        val itemName = MainActivity.wheels[0]

        composeTestRule.onNode(
            hasTestTag("list_item") and hasAnyDescendant(hasText(itemName)),
            useUnmergedTree = true
        ).performClick()

        composeTestRule
            .onNodeWithText("Not implemented on click: $itemName")
            .assertIsDisplayed()
    }
}