package com.donghanx.sets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.donghanx.design.ui.chip.FilterChipWithLeadingIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetTypeFilter() {
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    FilterChip(
        label = { Text(text = stringResource(id = R.string.type)) },
        selected = false,
        onClick = { showBottomSheet = true }
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState
        ) {
            SetTypesSelector(
                setTypes = stringArrayResource(R.array.set_types).toSet(),
                onSetTypeChanged = {
                    // TODO
                },
                onSetTypeReset = {
                    // TODO
                }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun SetTypesSelector(
    setTypes: Set<String>,
    onSetTypeChanged: (setType: String?) -> Unit,
    onSetTypeReset: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var selectedType by remember { mutableStateOf<String?>(null) }

        FlowRow(horizontalArrangement = Arrangement.Start) {
            setTypes.forEach { setType ->
                FilterChipWithLeadingIcon(
                    selected = selectedType == setType,
                    label = setType.toReadableSetTypeLabel(),
                    onClick = { selectedType = if (selectedType == setType) null else setType },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null,
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    },
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }

        Button(
            onClick = { onSetTypeChanged(selectedType) },
            modifier = Modifier.fillMaxWidth().height(40.dp),
        ) {
            Text(text = stringResource(id = R.string.view_results), fontSize = 16.sp)
        }

        TextButton(onClick = onSetTypeReset, modifier = Modifier.fillMaxWidth().height(40.dp)) {
            Text(text = stringResource(id = R.string.view_results), fontSize = 16.sp)
        }
    }
}

private fun String.toReadableSetTypeLabel(
    oldDelimiter: String = "_",
    newDelimiter: String = " "
): String =
    split(oldDelimiter).joinToString(separator = newDelimiter) { prevStr ->
        prevStr.replaceFirstChar { it.uppercase() }
    }
