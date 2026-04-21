package com.mightypotato.wheelie.ui.component.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mightypotato.wheelie.ui.theme.AppTheme

/**
 * A scrollable list of strings that supports item selection and deletion.
 *
 * If the list is empty, a placeholder message is displayed centered on the screen.
 *
 * @param itemsList The list of string data to be displayed in the list.
 * @param onDeleteClick Callback invoked when the delete icon is clicked for an item.
 * @param onItemClick Callback invoked when the body of a list item is clicked.
 */
@Composable
fun DeletableItemsList(
    itemsList: List<String>,
    onDeleteClick: (String) -> Unit = {},
    onItemClick: (String) -> Unit = {}
) {
    if(itemsList.isEmpty()){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .testTag("empty_list_placeholder"),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.testTag("empty_list_placeholder_text"),
                text = "No items available",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
    else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(itemsList) { item ->
                DeletableListItem(
                    name = item,
                    onDelete = { onDeleteClick(item) },
                    onItemClick = { onItemClick(item) }
                )
            }
        }
    }
}

/**
 * An individual item card used within [DeletableItemsList].
 *
 * Displays a headline text and a trailing delete icon button.
 *
 * @param name The text to display as the item's title.
 * @param onItemClick Callback for when the entire card is clicked.
 * @param onDelete Callback for when the delete button is clicked.
 */
@Composable
fun DeletableListItem(
    name: String,
    onItemClick: () -> Unit,
    onDelete: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
    ) {
        ListItem(
            modifier = Modifier
                .clickable { onItemClick.invoke() }
                .testTag("list_item"),
            colors = ListItemDefaults.colors(
                containerColor = androidx.compose.ui.graphics.Color.Transparent
            ),
            headlineContent = {
                Text(
                    modifier = Modifier.testTag("list_item_text"),
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            },
            trailingContent = {
                IconButton(onClick = onDelete) {
                    Icon(
                        modifier = Modifier.testTag("list_item_delete_button"),
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete item",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
        )
    }
}

/**
 * Preview provider for [DeletableItemsList] displaying sample data.
 */
@Preview(showBackground = true)
@Composable
fun WheelListPreview() {
    AppTheme {
        DeletableItemsList(listOf("Koło 1", "Koło 2", "Koło 3"))
    }
}
