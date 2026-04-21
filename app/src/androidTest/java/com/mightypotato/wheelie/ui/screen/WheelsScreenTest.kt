package com.mightypotato.wheelie.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mightypotato.wheelie.data.WheelsRepository
import com.mightypotato.wheelie.ui.view.model.WheelsViewModel
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

/**
 * Instrumented tests for the [WheelsScreen].
 *
 * Verifies that the screen correctly interacts with the [WheelsViewModel] to display data,
 * handle user clicks, and manage the visibility of the "Add Wheel" dialog.
 */
class WheelsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var repository: WheelsRepository
    private lateinit var viewModel: WheelsViewModel

    @Before
    fun setUp() {
        // Mock the repository to provide controlled data for the ViewModel.
        repository = mock(WheelsRepository::class.java)
        `when`(repository.getWheels()).thenReturn(flowOf(listOf("Wheel 1", "Wheel 2")))
        
        viewModel = WheelsViewModel(repository)
    }

    /**
     * Verifies that the screen renders the main components correctly.
     */
    @Test
    fun wheelsScreen_rendersCorrectly() {
        composeTestRule.setContent {
            WheelsScreen(viewModel = viewModel)
        }

        // Check if title and items are displayed
        composeTestRule.onNodeWithTag("screen_title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Wheel 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Wheel 2").assertIsDisplayed()
        composeTestRule.onNodeWithTag("add_wheel_button").assertIsDisplayed()
    }

    /**
     * Verifies that clicking the FAB opens the "Add Wheel" dialog.
     */
    @Test
    fun wheelsScreen_fabClick_opensDialog() {
        composeTestRule.setContent {
            WheelsScreen(viewModel = viewModel)
        }

        // Ensure dialog is not visible initially
        composeTestRule.onNodeWithText("Add wheel").assertDoesNotExist()

        // Click the FAB
        composeTestRule.onNodeWithTag("add_wheel_button").performClick()

        // Verify dialog is now visible
        composeTestRule.onNodeWithText("Add wheel").assertIsDisplayed()
    }

    /**
     * Verifies that dismissing the dialog works correctly.
     */
    @Test
    fun wheelsScreen_dialogDismiss_closesDialog() {
        composeTestRule.setContent {
            WheelsScreen(viewModel = viewModel)
        }

        // Open the dialog
        composeTestRule.onNodeWithTag("add_wheel_button").performClick()
        composeTestRule.onNodeWithText("Add wheel").assertIsDisplayed()

        // Click the Cancel button in the dialog
        composeTestRule.onNodeWithText("Cancel").performClick()

        // Verify dialog is gone
        composeTestRule.onNodeWithText("Add wheel").assertDoesNotExist()
    }
}
