package com.mightypotato.wheelie.ui.view.model.wheels

import com.mightypotato.wheelie.data.WheelsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class AddWheelDialogViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: WheelsRepository
    private lateinit var viewModel: AddWheelDialogViewModel

    // Helper to bypass Kotlin's null check when using Mockito matchers
    private fun <T> anyKotlin(): T = any<T>() ?: uninitialized()
    @Suppress("UNCHECKED_CAST")
    private fun <T> uninitialized(): T = null as T

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(WheelsRepository::class.java)
        viewModel = AddWheelDialogViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun displayDialog_resetsNameAndShowsDialog() {
        viewModel.onNewWheelNameChange("Some name")
        viewModel.displayDialog()

        assertTrue(viewModel.isAddWheelDialogVisible)
        assertEquals("", viewModel.newWheelName)
    }

    @Test
    fun onNewWheelNameChange_updatesState() {
        val newName = "Carbon Fiber"
        viewModel.onNewWheelNameChange(newName)

        assertEquals(newName, viewModel.newWheelName)
    }

    @Test
    fun onAddWheelConfirm_success_emitsSuccessEvent() = runTest {
        val wheelName = "New Wheel"
        viewModel.onNewWheelNameChange(wheelName)
        
        `when`(repository.insertWheel(anyKotlin())).thenReturn(1L)

        val events = mutableListOf<AddWheelDialogUiEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.events.collect { events.add(it) }
        }

        viewModel.onAddWheelConfirm(wheelName)
        advanceUntilIdle()

        assertTrue("Success event should be emitted", events.any { it is AddWheelDialogUiEvent.OnAddWheelSuccessEvent })
    }

    @Test
    fun onAddWheelConfirm_duplicate_emitsErrorEvent() = runTest {
        val wheelName = "Existing Wheel"
        viewModel.onNewWheelNameChange(wheelName)
        
        `when`(repository.insertWheel(anyKotlin())).thenReturn(-1L)

        val events = mutableListOf<AddWheelDialogUiEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.events.collect { events.add(it) }
        }

        viewModel.onAddWheelConfirm(wheelName)
        advanceUntilIdle()

        assertTrue("Error event should be emitted", events.any { it is AddWheelDialogUiEvent.OnAddWheelErrorEvent })
    }

    @Test
    fun hideAddWheelDialog_updatesState() {
        viewModel.displayDialog()
        viewModel.hideAddWheelDialog()
        assertFalse(viewModel.isAddWheelDialogVisible)
    }
}
