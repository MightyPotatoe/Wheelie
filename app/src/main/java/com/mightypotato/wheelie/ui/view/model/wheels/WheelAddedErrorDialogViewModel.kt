package com.mightypotato.wheelie.ui.view.model.wheels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * ViewModel responsible for managing the state of the error dialog 
 * shown when a wheel fails to be added (e.g., due to a naming conflict).
 */
class WheelAddedErrorDialogViewModel : ViewModel() {

    /**
     * Boolean flag indicating whether the error confirmation dialog should be shown.
     */
    var isAddWheelErrorDialogVisible by mutableStateOf(false)
        private set

    /**
     * Displays the error dialog by setting [isAddWheelErrorDialogVisible] to true.
     */
    fun showErrorDialog() {
        isAddWheelErrorDialogVisible = true
    }

    /**
     * Dismisses the error dialog by setting [isAddWheelErrorDialogVisible] to false.
     */
    fun onErrorDialogDismiss() {
        isAddWheelErrorDialogVisible = false
    }
}