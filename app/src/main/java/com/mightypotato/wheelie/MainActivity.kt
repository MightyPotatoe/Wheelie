package com.mightypotato.wheelie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mightypotato.wheelie.data.WheelsRepository
import com.mightypotato.wheelie.data.local.AppDatabase
import com.mightypotato.wheelie.ui.screen.WheelsScreen
import com.mightypotato.wheelie.ui.theme.AppTheme
import com.mightypotato.wheelie.ui.view.model.wheels.AddWheelDialogViewModel
import com.mightypotato.wheelie.ui.view.model.wheels.WheelAddedSuccessDialogViewModel
import com.mightypotato.wheelie.ui.view.model.wheels.WheelsViewModel

/**
 * The main entry point for the Wheelie application.
 *
 * This activity is responsible for:
 * - Initializing the local database and repository.
 * - Enabling edge-to-edge display.
 * - Setting up the Compose-based UI with the application theme and the main screen.
 */
class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is starting.
     *
     * Sets up the data layer and launches the [WheelsScreen] within the [AppTheme].
     *
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in [onSaveInstanceState]. Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Database and Repository
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = WheelsRepository(database.wheelDao())
        
        enableEdgeToEdge()
        setContent {
            AppTheme {
                // Pass the repository to the ViewModels
                WheelsScreen(
                    wheelsViewModel = WheelsViewModel(repository),
                    addWheelDialogViewModel = AddWheelDialogViewModel(repository),
                    wheelAddedSuccessDialogViewModel = WheelAddedSuccessDialogViewModel()
                )
            }
        }
    }
}
