package com.mightypotato.wheelie.ui.view.model.wheels

import com.mightypotato.wheelie.data.WheelsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Unit tests for [RemoveWheelDialogViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RemoveWheelDialogViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: WheelsRepository
    private lateinit var viewModel: RemoveWheelDialogViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(WheelsRepository::class.java)
        viewModel = RemoveWheelDialogViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Verifies that showing the dialog updates the state and sets the item name correctly.
     */
    @Test
    fun showRemoveWheelDialog_updatesState() {
        val wheelName = "Test Wheel"
        viewModel.showRemoveWheelDialog(wheelName)

        assertTrue(viewModel.isRemoveWheelDialogVisible)
        assertEquals(wheelName, viewModel.itemToRemoveName)
    }

    /**
     * Verifies that confirming the dialog calls the repository and hides the dialog.
     */
    @Test
    fun onConfirmRemoveWheelDialogDismiss_callsRepositoryAndHides() = runTest {
        val wheelName = "To Delete"
        viewModel.showRemoveWheelDialog(wheelName)

        viewModel.onConfirmRemoveWheelDialogDismiss()
        advanceUntilIdle()

        verify(repository).deleteWheelByName(wheelName)
        assertFalse(viewModel.isRemoveWheelDialogVisible)
    }

    /**
     * Verifies that dismissing the dialog (cancelling) hides it without calling the repository.
     */
    @Test
    fun onRemoveWheelDialogDismiss_hidesDialog() {
        viewModel.showRemoveWheelDialog("Any")
        viewModel.onRemoveWheelDialogDismiss()

        assertFalse(viewModel.isRemoveWheelDialogVisible)
    }
}
