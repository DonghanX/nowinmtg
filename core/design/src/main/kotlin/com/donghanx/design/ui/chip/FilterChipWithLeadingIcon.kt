package com.donghanx.design.ui.chip

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.donghanx.design.theme.NowInMTGTheme

@Composable
fun FilterChipWithLeadingIcon(
    selected: Boolean,
    label: String,
    onClick: () -> Unit,
    leadingIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text = label) },
        leadingIcon = if (selected) leadingIcon else null,
        modifier = modifier,
    )
}

@PreviewLightDark
@Composable
private fun FilterChipWithLeadingIconActivePreview() {
    NowInMTGTheme {
        FilterChipWithLeadingIcon(
            selected = true,
            label = "filter chip",
            onClick = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                )
            },
        )
    }
}

@PreviewLightDark
@Composable
private fun FilterChipWithLeadingIconInactivePreview() {
    NowInMTGTheme {
        FilterChipWithLeadingIcon(
            selected = false,
            label = "filter chip",
            onClick = {},
            leadingIcon = {},
        )
    }
}
