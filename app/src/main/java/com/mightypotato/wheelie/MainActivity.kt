package com.mightypotato.wheelie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import com.mightypotato.wheelie.ui.screen.WheelsScreen
import com.mightypotato.wheelie.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * The main entry point of the Wheelie application.
 *
 * This activity displays a list of "Wheels" and provides a Floating Action Button (FAB)
 * to add new wheels. It handles the primary UI layout using Jetpack Compose and
 * manages simple snackbar notifications for user interactions.
 */
class MainActivity : ComponentActivity() {

    companion object {
        /**
         * A hardcoded list of wheel names used to populate the initial UI state.
         */
        val wheels = listOf(
            "Koło Fortuny",
            "Koło Gospodyń Wiejskich",
            "Koło Zębate",
            "Koło Ratunkowe",
            "Koło Rowerowe",
            "Koło Samochodowe"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                WheelsScreen(
                    wheelList = wheels,
                    onAddWheelClick = ::onAddWheelButtonClick,
                    onItemClick = ::onItemClick,
                    onDeleteItemClick = ::onDeleteItemCLick
                )
            }
        }
    }

    /**
     * Handles the click event for the Add Wheel button.
     * Shows a "Not implemented" snackbar message.
     */
    private fun onAddWheelButtonClick(scope: CoroutineScope, snackbarHostState : SnackbarHostState) {
        scope.launch{
            snackbarHostState.showSnackbar("Not implemented yet.")
        }
    }

    /**
     * Handles the click event for an individual wheel item in the list.
     * Shows a snackbar message indicating which item was clicked.
     *
     * @param name The name of the wheel item that was clicked.
     */
    private fun onItemClick(name: String, scope: CoroutineScope, snackbarHostState : SnackbarHostState) {
        scope.launch{
            snackbarHostState.showSnackbar("Not implemented on click: $name")
        }
    }

    /**
     * Handles the click event for the delete action on a specific wheel item.
     * Shows a snackbar message indicating which item was requested to be deleted.
     *
     * @param name The name of the wheel item to be deleted.
     */
    private fun onDeleteItemCLick(name: String, scope: CoroutineScope, snackbarHostState : SnackbarHostState) {
        scope.launch{
            snackbarHostState.showSnackbar("Not implemented on delete: $name")
        }
    }
}

