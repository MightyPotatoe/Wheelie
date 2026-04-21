package com.mightypotato.wheelie.ui.view.model.wheels

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WheelAddedErrorDialogViewModelTest {

    private lateinit var viewModel: WheelAddedErrorDialogViewModel

    @Before
    fun setUp() {
        viewModel = WheelAddedErrorDialogViewModel()
    }

    @Test
    fun showSuccessDialog_updatesState() {
        viewModel.showErrorDialog()
        assertTrue(viewModel.isAddWheelErrorDialogVisible)
    }

    @Test
    fun onSuccessDialogDismiss_updatesState() {
        viewModel.showErrorDialog()
        assertTrue(viewModel.isAddWheelErrorDialogVisible)
        viewModel.onErrorDialogDismiss()
        assertFalse(viewModel.isAddWheelErrorDialogVisible)
    }
}
