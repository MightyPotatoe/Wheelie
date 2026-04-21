package com.mightypotato.wheelie.ui.view.model.wheels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WheelAddedSuccessDialogViewModel : ViewModel() {

    /**
     * Boolean flag indicating whether the success confirmation dialog should be shown.
     */
    var isAddWheelSuccessDialogVisible by mutableStateOf(false)
        private set

    fun showSuccessDialog() {
        isAddWheelSuccessDialogVisible = true
    }

    /**
     * Dismisses the success dialog.
     */
    fun onSuccessDialogDismiss() {
        isAddWheelSuccessDialogVisible = false
    }

}