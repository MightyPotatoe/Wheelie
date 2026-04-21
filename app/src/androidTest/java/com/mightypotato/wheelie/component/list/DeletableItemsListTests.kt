package com.mightypotato.wheelie.component.list

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
import com.mightypotato.wheelie.ui.component.list.DeletableItemsList
import org.junit.Rule
import org.junit.Test

class DeletableItemsListTests {

    @get:Rule
    val composeTestRule = createComposeRule()

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

        composeTestRule.onAllNodesWithTag("list_item").assertCountEquals(2)

        // Verify items are displayed
        composeTestRule.onNodeWithText("Item 1").assertIsDisplayed()
        composeTestRule.onNode(
            hasTestTag("list_item_delete_button") and
                    hasAnyAncestor(hasTestTag("list_item") and hasAnyDescendant(hasText("Item 1"))),
            useUnmergedTree = true
        ).assertIsDisplayed()


        composeTestRule.onNodeWithText("Item 2").assertIsDisplayed()
        composeTestRule.onNode(
            hasTestTag("list_item_delete_button") and
                    hasAnyAncestor(hasTestTag("list_item") and hasAnyDescendant(hasText("Item 2"))),
            useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun deletableItemsList_emptyList_displaysNoItems() {
        composeTestRule.setContent {
            DeletableItemsList(
                itemsList = emptyList(),
                onItemClick = { },
                onDeleteClick = { }
            )
        }
        composeTestRule.onAllNodesWithTag("list_item").assertCountEquals(0)
        composeTestRule.onNodeWithTag("empty_list_placeholder_text")
            .assertTextEquals("No items available")
    }

    @Test
    fun deletableItemsList_deleteClick_triggersCallback() {
        val items = listOf("Item 1")
        var deletedItem: String? = null

        composeTestRule.setContent {
            DeletableItemsList(
                itemsList = items,
                onItemClick = { /* No-op */ },
                onDeleteClick = { deletedItem = it }
            )
        }
        // Find and click the delete button specifically for "Item 1"
        composeTestRule.onNode(
            hasTestTag("list_item_delete_button"),
            useUnmergedTree = true
        ).performClick()
        // Assert the delete callback was triggered for the correct item
        assert(deletedItem == "Item 1")
    }

    @Test
    fun deletableItemsList_itemClick_triggersCallback() {
        val items = listOf("Item 1")
        var clickedItem: String? = null

        composeTestRule.setContent {
            DeletableItemsList(
                itemsList = items,
                onItemClick = { clickedItem = it },
                onDeleteClick = { /* No-op */ }
            )
        }
        // Find and click the delete button specifically for "Item 1"
        composeTestRule.onNode(
            hasTestTag("list_item"),
            useUnmergedTree = true
        ).performClick()
        // Assert the delete callback was triggered for the correct item
        assert(clickedItem == "Item 1")
    }
}