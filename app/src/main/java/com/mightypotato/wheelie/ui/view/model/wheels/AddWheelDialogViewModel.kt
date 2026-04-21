package com.mightypotato.wheelie.ui.view.model.wheels

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
 * Represents the one-time events related to the "Add Wheel" dialog.
 */
sealed class AddWheelDialogUiEvent {
    /** Event emitted when a wheel cannot be added (e.g., duplicate name). */
    data object OnAddWheelErrorEvent : AddWheelDialogUiEvent()
    /** Event emitted when a wheel is successfully added to the database. */
    data object OnAddWheelSuccessEvent : AddWheelDialogUiEvent()
}

/**
 * ViewModel responsible for managing the state and logic of the "Add Wheel" dialog.
 *
 * It handles showing/hiding the dialog, managing the input field state, and
 * persisting new wheels via the [WheelsRepository].
 *
 * @property repository The repository used to perform wheel data operations.
 */
class AddWheelDialogViewModel(private val repository: WheelsRepository) : ViewModel() {

    private val _events = MutableSharedFlow<AddWheelDialogUiEvent>()

    /**
     * A stream of [AddWheelDialogUiEvent]s that the UI should observe and handle.
     */
    val events = _events.asSharedFlow()

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

    /**
     * Resets the input field and displays the "Add Wheel" dialog.
     */
    fun displayDialog() {
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
     * Dismisses the "Add Wheel" dialog.
     */
    fun hideAddWheelDialog() {
        isAddWheelDialogVisible = false
    }

    /**
     * Attempts to persist the new wheel name to the database.
     *
     * Validates that the name is not blank and handles potential naming conflicts
     * by emitting an error event if the wheel already exists.
     *
     * @param newWheelName The name of the wheel to add.
     */
    fun onAddWheelConfirm(newWheelName: String) {
        if (newWheelName.isNotBlank()) {
            viewModelScope.launch {
                val newWheel = Wheel(name = newWheelName, owner = "Default")
                val result = repository.insertWheel(newWheel)
                //On error
                if (result == -1L) {
                    _events.emit(AddWheelDialogUiEvent.OnAddWheelErrorEvent)
                }
                //On success
                else {
                    _events.emit(AddWheelDialogUiEvent.OnAddWheelSuccessEvent)
                }
            }
        }
    }
}
