package com.mightypotato.wheelie.ui.view.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mightypotato.wheelie.data.WheelsRepository
import com.mightypotato.wheelie.data.local.Wheel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * Represents the various one-time events that can occur in the UI.
 *
 * These events are typically consumed by a [LaunchedEffect] in the UI to show
 * snackbars or perform navigation.
 */
sealed class UiEvent {
    /** Event triggered when a wheel is deleted. */
    data class OnDeleteButtonClickEvent(val message: String) : UiEvent()
    /** Event triggered when a wheel item is clicked. */
    data class OnItemClickEvent(val message: String) : UiEvent()
    /** Event triggered when a wheel is successfully added. */
    data class OnAddWheelButtonClickEvent(val message: String) : UiEvent()
    /** Event triggered when an error occurs during an operation. */
    data class OnErrorEvent(val message: String) : UiEvent()
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

    private val _events = MutableSharedFlow<UiEvent>()
    /**
     * A stream of [UiEvent]s that the UI should observe and handle.
     */
    val events = _events.asSharedFlow()

    /**
     * The list of wheel names to be displayed on the screen.
     * Automatically updated when the database changes.
     */
    var wheels by mutableStateOf<List<String>>(emptyList())
        private set

    /**
     * Boolean flag indicating whether the "Add Wheel" dialog should be shown.
     */
    var isAddWheelDialogVisible by mutableStateOf(false)
        private set

    /**
     * The current input value for the new wheel name in the dialog.
     */
    var newWheelName by mutableStateOf("")
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
     * Deletes a wheel by its name and emits a deletion event.
     *
     * @param name The name of the wheel to delete.
     */
    fun onDeleteButtonClick(name: String) {
        viewModelScope.launch {
            repository.deleteWheelByName(name)
            _events.emit(UiEvent.OnDeleteButtonClickEvent("Deleted $name"))
        }
    }

    /**
     * Handles a click event on a specific wheel item.
     *
     * @param name The name of the clicked wheel.
     */
    fun onItemClick(name: String) {
        viewModelScope.launch {
            _events.emit(UiEvent.OnItemClickEvent("Testing onItemClick $name"))
        }
    }

    /**
     * Prepares the state for showing the "Add Wheel" dialog.
     */
    fun onAddWheelButtonClick() {
        newWheelName = ""
        isAddWheelDialogVisible = true
    }

    /**
     * Updates the temporary wheel name as the user types in the dialog.
     *
     * @param newName The new string input from the user.
     */
    fun onNewWheelNameChange(newName: String) {
        newWheelName = newName
    }

    /**
     * Attempts to persist the new wheel name to the database.
     *
     * Validates that the name is not blank and handles potential naming conflicts
     * by emitting an error event if the wheel already exists.
     */
    fun onAddWheelConfirm() {
        if (newWheelName.isNotBlank()) {
            viewModelScope.launch {
                val newWheel = Wheel(name = newWheelName, owner = "Default")
                val result = repository.insertWheel(newWheel)
                if (result == -1L) {
                    _events.emit(UiEvent.OnErrorEvent("Wheel with name '$newWheelName' already exists!"))
                } else {
                    _events.emit(UiEvent.OnAddWheelButtonClickEvent("Added ${newWheel.name} to Database"))
                    isAddWheelDialogVisible = false
                }
            }
        }
    }

    /**
     * Dismisses the "Add Wheel" dialog without performing any actions.
     */
    fun onAddWheelDismiss() {
        isAddWheelDialogVisible = false
    }
}
