package com.mightypotato.wheelie.ui.component.list

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented UI tests for the [DeletableItemsList] component.
 *
 * These tests run on an Android device/emulator and use the Compose Test Rule
 * to verify UI rendering, state changes (like empty lists), and user interactions
 * such as item clicks and deletions.
 */
class DeletableItemsListTests {

    /**
     * Rule used to run Compose-related tests, allowing for content setting and node finding.
     */
    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Test: Item Rendering.
     *
     * Verifies that when a list of items is provided:
     * 1. The correct number of list items are displayed.
     * 2. The text for each item is visible.
     * 3. A delete button is rendered specifically for each item.
     */
    @Test
    fun deletableItemsList_displaysItems() {
        val items = listOf("Item 1", "Item 2")

        composeTestRule.setContent {
            DeletableItemsList(
                itemsList = items,
                onItemClick = { /* No-op */ },
                onDeleteClick = { /* No-op */ }
            )
        }

        // Check if correct number of items are rendered
        composeTestRule.onAllNodesWithTag("list_item").assertCountEquals(2)

        // Verify "Item 1" and its specific delete button are visible
        composeTestRule.onNodeWithText("Item 1").assertIsDisplayed()
        composeTestRule.onNode(
            hasTestTag("list_item_delete_button") and
                    hasAnyAncestor(hasTestTag("list_item") and hasAnyDescendant(hasText("Item 1"))),
            useUnmergedTree = true
        ).assertIsDisplayed()

        // Verify "Item 2" and its specific delete button are visible
        composeTestRule.onNodeWithText("Item 2").assertIsDisplayed()
        composeTestRule.onNode(
            hasTestTag("list_item_delete_button") and
                    hasAnyAncestor(hasTestTag("list_item") and hasAnyDescendant(hasText("Item 2"))),
            useUnmergedTree = true
        ).assertIsDisplayed()
    }

    /**
     * Test: Empty State.
     *
     * Verifies that if an empty list is passed to the component:
     * 1. The "No items available" placeholder text is shown.
     * 2. No actual list items are rendered on the screen.
     */
    @Test
    fun deletableItemsList_emptyList_displaysPlaceholder() {
        composeTestRule.setContent {
            DeletableItemsList(
                itemsList = emptyList(),
                onItemClick = { },
                onDeleteClick = { }
            )
        }

        composeTestRule.onNodeWithTag("empty_list_placeholder").assertIsDisplayed()
        composeTestRule.onNodeWithTag("empty_list_placeholder_text")
            .assertTextEquals("No items available")

        // Ensure no items are rendered
        composeTestRule.onAllNodesWithTag("list_item").assertCountEquals(0)
    }

    /**
     * Test: Item Interaction.
     *
     * Verifies that clicking the main body of a list item correctly triggers
     * the [onItemClick] callback with the expected item string.
     */
    @Test
    fun deletableItemsList_itemClick_triggersCallback() {
        val items = listOf("Target Item", "Other Item")
        var clickedItem: String? = null

        composeTestRule.setContent {
            DeletableItemsList(
                itemsList = items,
                onItemClick = { clickedItem = it },
                onDeleteClick = { /* No-op */ }
            )
        }

        // Click the specific item
        composeTestRule.onNodeWithText("Target Item").performClick()

        Assert.assertEquals("Target Item", clickedItem)
    }

    /**
     * Test: Deletion Interaction.
     *
     * Verifies that clicking the delete icon of a specific item triggers
     * the [onDeleteClick] callback for that specific item and no other.
     */
    @Test
    fun deletableItemsList_deleteClick_triggersCallback() {
        val items = listOf("Delete Me", "Keep Me")
        var deletedItem: String? = null

        composeTestRule.setContent {
            DeletableItemsList(
                itemsList = items,
                onItemClick = { /* No-op */ },
                onDeleteClick = { deletedItem = it }
            )
        }

        // Find and click the delete button specifically for "Delete Me"
        composeTestRule.onNode(
            hasTestTag("list_item_delete_button") and
                    hasAnyAncestor(hasTestTag("list_item") and hasAnyDescendant(hasText("Delete Me"))),
            useUnmergedTree = true
        ).performClick()

        Assert.assertEquals("Delete Me", deletedItem)
    }
}