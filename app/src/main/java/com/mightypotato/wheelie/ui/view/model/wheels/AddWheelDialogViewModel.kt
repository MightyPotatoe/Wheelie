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

sealed class AddWheelDialogUiEvent {
    data class OnErrorEvent(val message: String) : AddWheelDialogUiEvent()
    data object OnAddWheelSuccessEvent : AddWheelDialogUiEvent()
}

/**
 * ViewModel responsible for managing the state of alerts and dialogs.
 */
class AddWheelDialogViewModel(private val repository: WheelsRepository) : ViewModel() {

    private val _events = MutableSharedFlow<AddWheelDialogUiEvent>()
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
                if (result == -1L) {
                    _events.emit(AddWheelDialogUiEvent.OnErrorEvent("Wheel with name '$newWheelName' already exists!"))
                } else {
                    _events.emit(AddWheelDialogUiEvent.OnAddWheelSuccessEvent)
                }
            }
        }
    }
}
