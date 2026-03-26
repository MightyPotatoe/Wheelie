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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mightypotato.wheelie.ui.component.list.WheelList
import kotlinx.coroutines.CoroutineScope

/**
 * The main UI layout for the application.
 *
 * @param wheelList The list of items to display.
 * @param onAddWheelClick Callback for the Floating Action Button.
 * @param onItemClick Callback for clicking a list item.
 * @param onDeleteItemClick Callback for clicking the delete button on an item.
 */
@Composable
fun WheelsScreen(
    wheelList: List<String>,
    onAddWheelClick: (CoroutineScope, SnackbarHostState) -> Unit,
    onItemClick: (String, CoroutineScope, SnackbarHostState) -> Unit,
    onDeleteItemClick: (String, CoroutineScope, SnackbarHostState) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.testTag("add_wheel_button"),
                onClick = { onAddWheelClick(scope, snackbarHostState) }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add wheel button")
            }
        }
    ) { innerPadding ->
        MainContent(
            padding = innerPadding,
            wheelList = wheelList,
            onItemClick = { name -> onItemClick(name, scope, snackbarHostState) },
            onDeleteItemClick = { name -> onDeleteItemClick(name, scope, snackbarHostState) }
        )
    }
}

/**
 * Displays the main column content including the title and the [WheelList].
 */
@Composable
private fun MainContent(
    padding: PaddingValues,
    wheelList: List<String>,
    onItemClick: (String) -> Unit,
    onDeleteItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        //Title
        Text(
            text = "Wheels",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp)
                .testTag("screen_title")
        )
        //List of available wheels
        WheelList(
            wheelNames = wheelList,
            modifier = Modifier.testTag("wheel_list"),
            onItemClick = onItemClick,
            onDeleteClick = onDeleteItemClick
        )
    }
}