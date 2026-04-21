package com.mightypotato.wheelie.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.mightypotato.wheelie.data.WheelsRepository
import com.mightypotato.wheelie.ui.view.model.wheels.AddWheelDialogViewModel
import com.mightypotato.wheelie.ui.view.model.wheels.RemoveWheelDialogViewModel
import com.mightypotato.wheelie.ui.view.model.wheels.WheelAddedErrorDialogViewModel
import com.mightypotato.wheelie.ui.view.model.wheels.WheelAddedSuccessDialogViewModel
import com.mightypotato.wheelie.ui.view.model.wheels.WheelsViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

/**
 * Instrumented tests for the [WheelsScreen].
 *
 * Verifies that the screen correctly interacts with the [WheelsViewModel] and other ViewModels
 * to display data, handle user clicks, and manage the visibility of various dialogs.
 */
class WheelsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var repository: WheelsRepository
    private lateinit var viewModel: WheelsViewModel
    private lateinit var addWheelDialogViewModel: AddWheelDialogViewModel
    private lateinit var wheelAddedSuccessDialogViewModel: WheelAddedSuccessDialogViewModel
    private lateinit var wheelAddedErrorDialogViewModel: WheelAddedErrorDialogViewModel
    private lateinit var removeWheelDialogViewModel: RemoveWheelDialogViewModel


    // Helper to bypass Kotlin's null check when using Mockito matchers
    private fun <T> anyKotlin(): T = any<T>() ?: uninitialized()
    @Suppress("UNCHECKED_CAST")
    private fun <T> uninitialized(): T = null as T

    @Before
    fun setUp() {
        // Mock the repository to provide controlled data for the ViewModel.
        repository = mock(WheelsRepository::class.java)
        `when`(repository.getWheels()).thenReturn(flowOf(listOf("Wheel 1", "Wheel 2")))
        
        viewModel = WheelsViewModel(repository)
        addWheelDialogViewModel = AddWheelDialogViewModel(repository)
        wheelAddedSuccessDialogViewModel = WheelAddedSuccessDialogViewModel()
        wheelAddedErrorDialogViewModel = WheelAddedErrorDialogViewModel()
        removeWheelDialogViewModel = RemoveWheelDialogViewModel(repository)
    }

    /**
     * Verifies that the screen renders the main components correctly.
     */
    @Test
    fun wheelsScreen_rendersCorrectly() {
        composeTestRule.setContent {
            WheelsScreen(
                wheelsViewModel = viewModel,
                addWheelDialogViewModel = addWheelDialogViewModel,
                wheelAddedSuccessDialogViewModel = wheelAddedSuccessDialogViewModel,
                wheelAddedErrorDialogViewModel = wheelAddedErrorDialogViewModel,
                removeWheelDialogViewModel = removeWheelDialogViewModel
            )
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
            WheelsScreen(
                wheelsViewModel = viewModel,
                addWheelDialogViewModel = addWheelDialogViewModel,
                wheelAddedSuccessDialogViewModel = wheelAddedSuccessDialogViewModel,
                wheelAddedErrorDialogViewModel = wheelAddedErrorDialogViewModel,
                removeWheelDialogViewModel = removeWheelDialogViewModel
            )
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
            WheelsScreen(
                wheelsViewModel = viewModel,
                addWheelDialogViewModel = addWheelDialogViewModel,
                wheelAddedSuccessDialogViewModel = wheelAddedSuccessDialogViewModel,
                wheelAddedErrorDialogViewModel = wheelAddedErrorDialogViewModel,
                removeWheelDialogViewModel = removeWheelDialogViewModel
            )
        }

        // Open the dialog
        composeTestRule.onNodeWithTag("add_wheel_button").performClick()
        composeTestRule.onNodeWithText("Add wheel").assertIsDisplayed()

        // Click the Cancel button in the dialog
        composeTestRule.onNodeWithText("Cancel").performClick()

        // Verify dialog is gone
        composeTestRule.onNodeWithText("Add wheel").assertDoesNotExist()
    }

    /**
     * Verifies that adding a wheel correctly shows the success dialog and then dismisses it.
     */
    @Test
    fun wheelsScreen_addWheel_showsSuccessDialog() {
        // Mock successful insertion. Note: insertWheel is a suspend function.
        // We use runBlocking here to allow calling the suspend function for Mockito setup.
        runBlocking {
            `when`(repository.insertWheel(anyKotlin())).thenReturn(1L)
        }

        composeTestRule.setContent {
            WheelsScreen(
                wheelsViewModel = viewModel,
                addWheelDialogViewModel = addWheelDialogViewModel,
                wheelAddedSuccessDialogViewModel = wheelAddedSuccessDialogViewModel,
                wheelAddedErrorDialogViewModel = wheelAddedErrorDialogViewModel,
                removeWheelDialogViewModel = removeWheelDialogViewModel
            )
        }

        // 1. Open dialog
        composeTestRule.onNodeWithTag("add_wheel_button").performClick()

        // 2. Input name
        composeTestRule.onNodeWithText("Wheel name").performTextInput("New Wheel")

        // 3. Confirm addition
        composeTestRule.onNodeWithText("Add").performClick()

        // 4. Verify Success Dialog appears
        composeTestRule.onNodeWithText("Done!").assertIsDisplayed()
        composeTestRule.onNodeWithText("Wheel 'New Wheel' added successfully").assertIsDisplayed()

        // 5. Dismiss Success Dialog
        composeTestRule.onNodeWithText("OK").performClick()

        // 6. Verify Success Dialog is gone
        composeTestRule.onNodeWithText("Done!").assertDoesNotExist()
    }


    /**
     * Verifies that the screen shows a placeholder when the list of wheels is empty.
     */
    @Test
    fun wheelsScreen_emptyList_showsPlaceholder() {
        `when`(repository.getWheels()).thenReturn(flowOf(emptyList()))
        viewModel = WheelsViewModel(repository)

        composeTestRule.setContent {
            WheelsScreen(
                wheelsViewModel = viewModel,
                addWheelDialogViewModel = addWheelDialogViewModel,
                wheelAddedSuccessDialogViewModel = wheelAddedSuccessDialogViewModel,
                wheelAddedErrorDialogViewModel = wheelAddedErrorDialogViewModel,
                removeWheelDialogViewModel = removeWheelDialogViewModel
            )
        }

        composeTestRule.onNodeWithTag("empty_list_placeholder").assertIsDisplayed()
        composeTestRule.onNodeWithText("No items available").assertIsDisplayed()
    }

    /**
     * Verifies that adding a wheel correctly shows the error dialog and then dismisses it.
     */
    @Test
    fun wheelsScreen_addWheel_showsErrorDialog() {
        // Mock failed insertion. Note: insertWheel is a suspend function.
        // We use runBlocking here to allow calling the suspend function for Mockito setup.
        runBlocking {
            `when`(repository.insertWheel(anyKotlin())).thenReturn(-1L)
        }

        composeTestRule.setContent {
            WheelsScreen(
                wheelsViewModel = viewModel,
                addWheelDialogViewModel = addWheelDialogViewModel,
                wheelAddedSuccessDialogViewModel = wheelAddedSuccessDialogViewModel,
                wheelAddedErrorDialogViewModel = wheelAddedErrorDialogViewModel,
                removeWheelDialogViewModel = removeWheelDialogViewModel
            )
        }

        // 1. Open dialog
        composeTestRule.onNodeWithTag("add_wheel_button").performClick()

        // 2. Input name
        composeTestRule.onNodeWithText("Wheel name").performTextInput("New Wheel")

        // 3. Confirm addition
        composeTestRule.onNodeWithText("Add").performClick()

        // 4. Verify Error Dialog appears
        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
        composeTestRule.onNodeWithText("Wheel 'New Wheel' could not be added. Verify if your wheel does not already exist").assertIsDisplayed()

        // 5. Dismiss Error Dialog
        composeTestRule.onNodeWithText("OK").performClick()

        // 6. Verify Error Dialog is gone
        composeTestRule.onNodeWithText("Error").assertDoesNotExist()
    }

    /**
     * Verifies that clicking the delete button on an item shows the remove wheel dialog
     * and confirms deletion.
     */
    @Test
    fun wheelsScreen_deleteWheel_showsRemoveDialogAndConfirms() {
        val wheelName = "Wheel 1"

        composeTestRule.setContent {
            WheelsScreen(
                wheelsViewModel = viewModel,
                addWheelDialogViewModel = addWheelDialogViewModel,
                wheelAddedSuccessDialogViewModel = wheelAddedSuccessDialogViewModel,
                wheelAddedErrorDialogViewModel = wheelAddedErrorDialogViewModel,
                removeWheelDialogViewModel = removeWheelDialogViewModel
            )
        }

        // 1. Click delete button on the first wheel
        composeTestRule.onAllNodesWithTag("list_item_delete_button", useUnmergedTree = true)[0].performClick()

        // 2. Verify Remove Dialog appears
        composeTestRule.onNodeWithText("Delete wheel").assertIsDisplayed()
        
        // 3. Confirm deletion
        composeTestRule.onNodeWithText("Delete").performClick()

        // 4. Verify dialog is gone
        composeTestRule.onNodeWithText("Delete wheel").assertDoesNotExist()
        
        // 5. Verify repository call
        runBlocking {
            verify(repository).deleteWheelByName(wheelName)
        }
    }
}
