package com.donghanx.design.ui.card

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableCard(
    headerTitle: String,
    modifier: Modifier = Modifier,
    showHeaderWhenExpanded: Boolean = true,
    expandedContent: @Composable () -> Unit = {},
) {
    val (expanded, setExpanded) = rememberSaveable { mutableStateOf(true) }

    ExpandableCard(
        headerTitle = headerTitle,
        expanded = expanded,
        onSetExpanded = setExpanded,
        expandedContent = expandedContent,
        modifier = modifier,
        showHeaderWhenExpanded = showHeaderWhenExpanded,
    )
}

@Composable
private fun ExpandableCard(
    headerTitle: String,
    expanded: Boolean,
    onSetExpanded: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    showHeaderWhenExpanded: Boolean = false,
    expandedContent: @Composable () -> Unit = {},
) {
    Card(
        modifier =
            modifier.fillMaxWidth().wrapContentHeight().clickable { onSetExpanded(!expanded) }
    ) {
        Column(modifier = Modifier.padding(all = 8.dp)) {
            if (showHeaderWhenExpanded || !expanded) {
                ExpandableCardHeader(expanded = expanded, headerTitle = headerTitle)
            }

            AnimatedContent(targetState = expanded, label = "Expandable Card") { expanded ->
                if (expanded) {
                    Column {
                        Spacer(modifier = modifier.height(4.dp))

                        expandedContent()
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpandableCardHeader(
    expanded: Boolean,
    headerTitle: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Row {
        Text(text = headerTitle, fontWeight = FontWeight.Medium)
        Spacer(modifier = modifier.width(4.dp))
        Icon(
            imageVector = if (expanded) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropUp,
            contentDescription = contentDescription,
        )
    }
}

@Preview
@Composable
private fun ExpandableCardExpandedPreview() {
    ExpandableCard(
        headerTitle = "Title",
        expanded = true,
        onSetExpanded = {},
        showHeaderWhenExpanded = true,
    ) {
        Text(text = "Expanded content")
    }
}

@Preview
@Composable
private fun ExpandableCardCollapsePreview() {
    ExpandableCard(headerTitle = "Title", expanded = false, onSetExpanded = {}) {
        Text(text = "Expanded content")
    }
}
