package com.mightypotato.wheelie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mightypotato.wheelie.data.WheelsRepository
import com.mightypotato.wheelie.data.local.AppDatabase
import com.mightypotato.wheelie.ui.screen.SpinnerScreen
import com.mightypotato.wheelie.ui.theme.AppTheme
import com.mightypotato.wheelie.ui.view.model.spinner.SpinnerViewModel

/**
 * Activity that displays the spinner for a specific wheel.
 */
class SpinnerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val wheelName = intent.getStringExtra("WHEEL_NAME") ?: "Unknown Wheel"

        // Initialize Database and Repository
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = WheelsRepository(database.wheelDao())
        val spinnerViewModel = SpinnerViewModel(wheelName, repository)

        enableEdgeToEdge()
        setContent {
            AppTheme {
                SpinnerScreen(viewModel = spinnerViewModel)
            }
        }
    }
}
