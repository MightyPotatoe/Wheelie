package com.mightypotato.wheelie.ui.view.model.wheels

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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
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
    fun onAddWheelButtonClick_emitsEvent() = runTest {
        val events = mutableListOf<WheelsViewModelUiEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.events.collect { events.add(it) }
        }

        viewModel.onAddWheelButtonClick()
        advanceUntilIdle()

        assertTrue("Add wheel button click event should be emitted", events.any { it is WheelsViewModelUiEvent.OnAddWheelButtonClickEvent })
    }

    @Test
    fun onDeleteButtonClick_EmitsEventWithCorrectName() = runTest {
        val name = "To Delete"
        
        val events = mutableListOf<WheelsViewModelUiEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.events.collect { events.add(it) }
        }

        viewModel.onDeleteButtonClick(name)
        advanceUntilIdle()

        val event = events.filterIsInstance<WheelsViewModelUiEvent.OnDeleteButtonClickEvent>().firstOrNull()
        assertTrue("Delete event should be emitted", event != null)
        assertEquals("Event should contain the correct wheel name", name, event?.message)
    }

    @Test
    fun onItemClick_emitsOnItemClickEventWithCorrectName() = runTest {
        val name = "Test Wheel"
        val events = mutableListOf<WheelsViewModelUiEvent>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)){
            viewModel.events.collect { events.add(it) }
        }

        viewModel.onItemClick(name)
        advanceUntilIdle()

        val event = events.filterIsInstance<WheelsViewModelUiEvent.OnItemClickEvent>().firstOrNull()
        assertTrue("Item click event should be emitted", event != null)
        assertEquals("Event should contain the correct wheel name", name, event?.wheelName)
    }
}
