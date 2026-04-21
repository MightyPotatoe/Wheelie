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
 * Represents the various one-time events that can occur in the UI.
 *
 * These events are typically consumed by a [LaunchedEffect] in the UI to show
 * snackbars or perform navigation.
 */
sealed class WheelsViewModelUiEvent {
    /** Event triggered when a wheel is deleted. */
    data class OnDeleteButtonClickEvent(val message: String) : WheelsViewModelUiEvent()
    /** Event triggered when a wheel item is clicked. */
    data class OnItemClickEvent(val message: String) : WheelsViewModelUiEvent()
    /** Event triggered when an error occurs during an operation. */
    data class OnErrorEvent(val message: String) : WheelsViewModelUiEvent()
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
     * Prepares the state for showing the "Add Wheel" dialog.
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
            repository.deleteWheelByName(name)
            _events.emit(WheelsViewModelUiEvent.OnDeleteButtonClickEvent("Deleted $name"))
        }
    }

    /**
     * Handles a click event on a specific wheel item.
     *
     * @param name The name of the clicked wheel.
     */
    fun onItemClick(name: String) {
        viewModelScope.launch {
            _events.emit(WheelsViewModelUiEvent.OnItemClickEvent("Testing onItemClick $name"))
        }
    }
}
