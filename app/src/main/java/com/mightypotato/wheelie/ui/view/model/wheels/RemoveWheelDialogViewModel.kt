package com.mightypotato.wheelie.ui.view.model.wheels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mightypotato.wheelie.data.WheelsRepository
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing the state and logic of the wheel removal confirmation dialog.
 *
 * It handles showing/hiding the dialog and coordinating the deletion of a wheel
 * via the [WheelsRepository].
 *
 * @property repository The repository used to perform wheel data operations.
 */
class RemoveWheelDialogViewModel(private val repository: WheelsRepository) : ViewModel() {

    /**
     * The name of the wheel that is currently targeted for removal.
     */
    var itemToRemoveName = ""

    /**
     * Boolean flag indicating whether the removal confirmation dialog should be shown.
     */
    var isRemoveWheelDialogVisible by mutableStateOf(false)
        private set

    /**
     * Displays the removal confirmation dialog for a specific wheel.
     *
     * @param wheelName The name of the wheel to be removed.
     */
    fun showRemoveWheelDialog(wheelName: String) {
        isRemoveWheelDialogVisible = true
        itemToRemoveName = wheelName
    }

    /**
     * Confirms the removal of the targeted wheel and dismisses the dialog.
     *
     * Launches a coroutine to delete the wheel from the repository by name.
     */
    fun onConfirmRemoveWheelDialogDismiss() {
        viewModelScope.launch {
            repository.deleteWheelByName(itemToRemoveName)
            isRemoveWheelDialogVisible = false
        }
    }

    /**
     * Dismisses the removal confirmation dialog without performing any action.
     */
    fun onRemoveWheelDialogDismiss() {
        isRemoveWheelDialogVisible = false
    }

}
