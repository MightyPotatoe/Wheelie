package com.mightypotato.wheelie.ui.component.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mightypotato.wheelie.ui.theme.AppTheme

@Composable
fun WheelList(
    wheelNames: List<String>,
    modifier: Modifier = Modifier,
    onDeleteClick: (String) -> Unit = {},
    onItemClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(wheelNames) { name ->
            WheelItem(
                name = name,
                onDelete = { onDeleteClick(name) },
                onItemClick = { onItemClick(name) }
            )
        }
    }

}

@Composable
fun WheelItem(name: String, onItemClick: () -> Unit, onDelete: () -> Unit) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
    ) {
        ListItem(
            modifier = Modifier.clickable{onItemClick.invoke()},
            colors = ListItemDefaults.colors(
                containerColor = androidx.compose.ui.graphics.Color.Transparent
            ),
            headlineContent = {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            trailingContent = {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Usuń",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WheelListPreview() {
    AppTheme {
        WheelList(listOf("Koło 1", "Koło 2", "Koło 3"))
    }
}
