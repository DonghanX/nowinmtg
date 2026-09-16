package com.donghanx.design.ui.expandable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.donghanx.design.theme.NowInMTGTheme

@Composable
fun ExpandableSection(
    headerTitle: String,
    modifier: Modifier = Modifier,
    initialExpanded: Boolean = true,
    expandedContent: @Composable () -> Unit = {},
) {
    val (expanded, setExpanded) = rememberSaveable { mutableStateOf(initialExpanded) }

    ExpandableSection(
        headerTitle = headerTitle,
        expanded = expanded,
        onSetExpanded = setExpanded,
        expandedContent = expandedContent,
        modifier = modifier,
    )
}

@Composable
private fun ExpandableSection(
    headerTitle: String,
    expanded: Boolean,
    onSetExpanded: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    expandedContent: @Composable () -> Unit = {},
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(modifier = Modifier.clickable { onSetExpanded(!expanded) }) {
            ExpandableCardHeader(expanded = expanded, headerTitle = headerTitle)
        }

        AnimatedVisibility(visible = expanded) {
            Column {
                Spacer(modifier = modifier.height(4.dp))

                expandedContent()
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

        val arrowRotation by
            animateFloatAsState(targetValue = if (expanded) 0F else 180F, label = "arrowRotation")

        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.rotate(arrowRotation),
        )
    }
}

@PreviewLightDark
@Composable
private fun ExpandableCardExpandedPreview() {
    NowInMTGTheme {
        ExpandableSection(
            headerTitle = "Title",
            expanded = true,
            onSetExpanded = {},
            modifier = Modifier.padding(horizontal = 8.dp),
        ) {
            Text(text = "Expanded content")
        }
    }
}

@PreviewLightDark
@Composable
private fun ExpandableCardCollapsePreview() {
    NowInMTGTheme {
        ExpandableSection(
            headerTitle = "Title",
            expanded = false,
            onSetExpanded = {},
            modifier = Modifier.padding(horizontal = 8.dp),
        ) {
            Text(text = "Expanded content")
        }
    }
}
