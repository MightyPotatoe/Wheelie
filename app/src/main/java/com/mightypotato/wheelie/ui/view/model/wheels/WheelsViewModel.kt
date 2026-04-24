package com.mightypotato.wheelie.ui.view.model.wheels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mightypotato.wheelie.data.WheelsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * Represents the various one-time events that can occur in the UI of the Wheels screen.
 */
sealed class WheelsViewModelUiEvent {
    /**
     * Event triggered when a wheel is requested to be deleted.
     * @property message The name of the wheel to delete.
     */
    data class OnDeleteButtonClickEvent(val message: String) : WheelsViewModelUiEvent()

    /**
     * Event triggered to navigate to the spinner screen for a specific wheel.
     * @property wheelName The name of the wheel to display in the spinner.
     */
    data class OnItemClickEvent(val wheelName: String) : WheelsViewModelUiEvent()

    /**
     * Event triggered when the "Add Wheel" button is clicked.
     */
    class OnAddWheelButtonClickEvent : WheelsViewModelUiEvent()
}

/**
 * ViewModel responsible for managing the state and logic of the Wheels screen.
 *
 * It interacts with the [WheelsRepository] to persist data and exposes the state
 * to the UI via Compose [mutableStateOf] properties.
 *
 * @property repository The repository used for wheel data operations.
 */
class WheelsViewModel(private val repository: WheelsRepository) : ViewModel() {

    private val _events = MutableSharedFlow<WheelsViewModelUiEvent>()
    /**
     * A stream of [WheelsViewModelUiEvent]s that the UI should observe and handle.
     */
    val events = _events.asSharedFlow()

    /**
     * The list of wheel names to be displayed on the screen.
     * Automatically updated when the database changes.
     */
    var wheels by mutableStateOf<List<String>>(emptyList())
        private set


    init {
        // Collect wheels from the repository and update the UI state
        viewModelScope.launch {
            repository.getWheels().collect {
                wheels = it
            }
        }
    }

    /**
     * Prepares the state for showing the "Add Wheel" dialog by emitting an event.
     */
    fun onAddWheelButtonClick() {
        viewModelScope.launch {
            _events.emit(WheelsViewModelUiEvent.OnAddWheelButtonClickEvent())
        }
    }

    /**
     * Deletes a wheel by its name and emits a deletion event.
     *
     * @param name The name of the wheel to delete.
     */
    fun onDeleteButtonClick(name: String) {
        viewModelScope.launch {
            _events.emit(WheelsViewModelUiEvent.OnDeleteButtonClickEvent(name))
        }
    }

    /**
     * Handles a click on a wheel item by emitting a navigation event.
     *
     * @param name The name of the wheel that was clicked.
     */
    fun onItemClick(name: String) {
        viewModelScope.launch {
            _events.emit(WheelsViewModelUiEvent.OnItemClickEvent(name))
        }
    }
}
