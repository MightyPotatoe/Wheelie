package com.mightypotato.wheelie.ui.view.model

import com.mightypotato.wheelie.data.WheelsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

/**
 * Unit test for [WheelsViewModel].
 *
 * Uses a [StandardTestDispatcher] for virtual time control and Mockito for repository mocking.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class WheelsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: WheelsRepository
    private lateinit var viewModel: WheelsViewModel

    // Helper to bypass Kotlin's null check when using Mockito matchers
    private fun <T> anyKotlin(): T = any<T>() ?: uninitialized()
    @Suppress("UNCHECKED_CAST")
    private fun <T> uninitialized(): T = null as T

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(WheelsRepository::class.java)
        
        // Default behavior for getWheels
        `when`(repository.getWheels()).thenReturn(flowOf(emptyList()))
        
        viewModel = WheelsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun init_collectsWheelsFromRepository() = runTest {
        val wheelNames = listOf("Wheel A", "Wheel B")
        `when`(repository.getWheels()).thenReturn(flowOf(wheelNames))
        
        // Re-initialize to trigger init block with new mock behavior
        viewModel = WheelsViewModel(repository)
        advanceUntilIdle()

        assertEquals(wheelNames, viewModel.wheels)
    }

    @Test
    fun onAddWheelButtonClick_resetsNameAndShowsDialog() {
        viewModel.onAddWheelButtonClick()

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
    fun onAddWheelConfirm_success_emitsEventAndClosesDialog() = runTest {
        // Setup: Open dialog and set a name
        viewModel.onAddWheelButtonClick()
        viewModel.onNewWheelNameChange("New Wheel")
        
        // Mock successful insertion using anyKotlin() helper
        `when`(repository.insertWheel(anyKotlin())).thenReturn(1L)

        val events = mutableListOf<UiEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.events.collect { events.add(it) }
        }

        viewModel.onAddWheelConfirm()
        advanceUntilIdle()

        assertTrue("Success event should be emitted", events.any { it is UiEvent.OnAddWheelButtonClickEvent })
        assertFalse("Dialog should be hidden on success", viewModel.isAddWheelDialogVisible)
    }

    @Test
    fun onAddWheelConfirm_duplicate_emitsErrorEvent() = runTest {
        // Setup: Open dialog and set a name
        viewModel.onAddWheelButtonClick()
        viewModel.onNewWheelNameChange("Existing Wheel")
        
        // Mock duplicate error (-1L) using anyKotlin() helper
        `when`(repository.insertWheel(anyKotlin())).thenReturn(-1L)

        val events = mutableListOf<UiEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.events.collect { events.add(it) }
        }

        viewModel.onAddWheelConfirm()
        advanceUntilIdle()

        assertTrue("Error event should be emitted", events.any { it is UiEvent.OnErrorEvent })
        assertTrue("Dialog should remain visible on error", viewModel.isAddWheelDialogVisible)
    }

    @Test
    fun onDeleteButtonClick_callsRepositoryAndEmitsEvent() = runTest {
        val name = "To Delete"
        
        val events = mutableListOf<UiEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.events.collect { events.add(it) }
        }

        viewModel.onDeleteButtonClick(name)
        advanceUntilIdle()

        verify(repository).deleteWheelByName(name)
        assertTrue("Delete event should be emitted", events.any { it is UiEvent.OnDeleteButtonClickEvent })
    }
}
