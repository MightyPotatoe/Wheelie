package com.mightypotato.wheelie.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mightypotato.wheelie.ui.component.dialog.TwoButtonDialogWithInput
import com.mightypotato.wheelie.ui.component.list.DeletableItemsList
import com.mightypotato.wheelie.ui.view.model.UiEvent
import com.mightypotato.wheelie.ui.view.model.WheelsViewModel
import kotlinx.coroutines.launch

/**
 * The primary screen for managing and viewing wheels.
 *
 * This screen serves as the main entry point for the "Wheels" feature. It handles:
 * - Observing the list of wheels from the [WheelsViewModel].
 * - Displaying a [Scaffold] with a [FloatingActionButton] to trigger the addition of new wheels.
 * - Showing a [TwoButtonDialogWithInput] for user input when adding a wheel.
 * - Collecting and displaying [UiEvent]s (like success or error messages) via a [SnackbarHost].
 *
 * @param viewModel The [WheelsViewModel] that manages the UI state and processes business logic.
 */
@Composable
fun WheelsScreen(
    viewModel: WheelsViewModel,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Collects one-time UI events from the ViewModel and displays them in a Snackbar.
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when(event){
                is UiEvent.OnDeleteButtonClickEvent -> {
                    scope.launch{
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
                is UiEvent.OnItemClickEvent -> {
                    scope.launch{
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
                is UiEvent.OnAddWheelButtonClickEvent -> {
                    scope.launch{
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
                is UiEvent.OnErrorEvent -> {
                    scope.launch{
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }
    }

    // Displays the "Add Wheel" dialog when triggered by the FAB.
    if (viewModel.isAddWheelDialogVisible) {
        TwoButtonDialogWithInput(
            dialogTitle = "Add wheel",
            dialogMessage = "Enter new wheel name:",
            inputLabel = "Wheel name",
            inputValue = viewModel.newWheelName,
            confirmButtonText ="Add",
            cancelButtonText = "Cancel",
            onNameChange = { viewModel.onNewWheelNameChange(it) },
            onConfirm = { viewModel.onAddWheelConfirm() },
            onDismiss = { viewModel.onAddWheelDismiss() }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.testTag("add_wheel_button"),
                onClick = { viewModel.onAddWheelButtonClick() }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add wheel button")
            }
        }
    ) { innerPadding ->
        MainContent(
            viewModel = viewModel,
            padding = innerPadding
        )
    }
}

/**
 * The main scrollable content area of the wheels screen.
 *
 * Renders the screen title and the [DeletableItemsList] that displays the collection of wheels.
 *
 * @param viewModel The [WheelsViewModel] used to access the list of wheels and handle interaction callbacks.
 * @param padding The [PaddingValues] provided by the parent [Scaffold] to ensure proper layout spacing.
 */
@Composable
fun MainContent(
    viewModel: WheelsViewModel,
    padding: PaddingValues,
) {

    val wheels = viewModel.wheels

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        // Render the screen header title.
        Text(
            text = "Wheels",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp)
                .testTag("screen_title")
        )
        
        // Render the list of available wheels.
        DeletableItemsList(
            itemsList = wheels,
            onItemClick =  { name -> viewModel.onItemClick(name) },
            onDeleteClick = {name -> viewModel.onDeleteButtonClick(name)}
        )
    }
}
