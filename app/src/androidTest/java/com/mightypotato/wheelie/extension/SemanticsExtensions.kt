package com.mightypotato.wheelie.extension

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsNodeInteractionCollection

/**
 * Extracts and returns the text content from a collection of semantics nodes.
 *
 * This extension is useful for verifying the content of multiple UI elements at once,
 * such as checking all items in a list against an expected data source.
 *
 * @return A list of strings containing the text found in each node of the collection.
 * If a node does not contain text, an empty string is returned for that position.
 */
fun SemanticsNodeInteractionCollection.fetchTextContents(): List<String> {
    return this.fetchSemanticsNodes().map { node ->
        node.config.getOrNull(SemanticsProperties.Text)?.joinToString("") ?: ""
    }
}