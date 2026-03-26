package com.mightypotato.wheelie

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mightypotato.wheelie.ui.component.list.WheelList
import com.mightypotato.wheelie.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    /**
     * Hardcoded wheel items
     */
    val wheels = listOf(
        "Koło Fortuny",
        "Koło Gospodyń Wiejskich",
        "Koło Zębate",
        "Koło Ratunkowe",
        "Koło Rowerowe",
        "Koło Samochodowe"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = { onAddWheelButtonClick() }) {
                            Icon(Icons.Filled.Add, "Add wheel button")
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "Wheels",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .padding(top = 24.dp)
                        )
                        WheelList(
                            wheelNames = wheels,
                            modifier = Modifier.padding(innerPadding),
                            onItemClick = { name -> onItemClick(name) },
                            onDeleteClick = { name -> onDeleteItemCLick(name) }
                        )
                    }
                }
            }
        }
    }

    private fun onAddWheelButtonClick() {
        Toast.makeText(this, "Not implemented yet.", Toast.LENGTH_SHORT).show()
    }

    private fun onItemClick(name: String) {
        Toast.makeText(this, "Not implemented yet for: $name", Toast.LENGTH_SHORT).show()
    }

    private fun onDeleteItemCLick(name: String) {
        Toast.makeText(this, "Not implemented yet for: $name", Toast.LENGTH_SHORT).show()
    }
}

