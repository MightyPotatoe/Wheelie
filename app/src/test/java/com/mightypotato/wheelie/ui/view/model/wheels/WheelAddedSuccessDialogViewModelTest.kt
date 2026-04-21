package com.mightypotato.wheelie.ui.view.model.wheels

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WheelAddedSuccessDialogViewModelTest {

    private lateinit var viewModel: WheelAddedSuccessDialogViewModel

    @Before
    fun setUp() {
        viewModel = WheelAddedSuccessDialogViewModel()
    }

    @Test
    fun showSuccessDialog_updatesState() {
        viewModel.showSuccessDialog()
        assertTrue(viewModel.isAddWheelSuccessDialogVisible)
    }

    @Test
    fun onSuccessDialogDismiss_updatesState() {
        viewModel.showSuccessDialog()
        viewModel.onSuccessDialogDismiss()
        assertFalse(viewModel.isAddWheelSuccessDialogVisible)
    }
}
