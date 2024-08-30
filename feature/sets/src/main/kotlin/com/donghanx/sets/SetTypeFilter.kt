package com.donghanx.sets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.donghanx.common.extensions.capitalize
import com.donghanx.design.ui.chip.FilterChipWithLeadingIcon
import com.donghanx.sets.composable.BottomSheetContentWrapper
import com.donghanx.sets.composable.FilterChipWithBottomSheetContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SetTypeFilter(selectedSetType: String?, onSetTypeChanged: (setType: String?) -> Unit) {
    val scope = rememberCoroutineScope()
    val (showBottomSheet, onShowBottomSheetChange) = remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    FilterChipWithBottomSheetContent(
        label = {
            Text(text = selectedSetType?.toReadableSetTypeLabel() ?: stringResource(R.string.type))
        },
        bottomSheetState = bottomSheetState,
        selected = selectedSetType != null,
        showBottomSheet = showBottomSheet,
        onShowBottomSheetChange = onShowBottomSheetChange,
    ) {
        SetTypesSelector(
            initialSelectedType = selectedSetType,
            setTypes = stringArrayResource(R.array.set_types).toSet(),
            onSetTypeChanged = onSetTypeChanged,
            onHideBottomSheet = {
                scope
                    .launch { bottomSheetState.hide() }
                    .invokeOnCompletion { onShowBottomSheetChange(false) }
            },
        )
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun SetTypesSelector(
    initialSelectedType: String?,
    setTypes: Set<String>,
    onSetTypeChanged: (setType: String?) -> Unit,
    onHideBottomSheet: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        var selectedType by remember { mutableStateOf(initialSelectedType) }

        BottomSheetContentWrapper(
            onViewResultsClick = {
                onSetTypeChanged(selectedType)
                onHideBottomSheet()
            },
            onResetClick = {
                onSetTypeChanged(null)
                onHideBottomSheet()
            },
        ) {
            FlowRow(horizontalArrangement = Arrangement.Start) {
                setTypes.forEach { setType ->
                    val setTypeLabel = remember { setType.toReadableSetTypeLabel() }

                    FilterChipWithLeadingIcon(
                        selected = selectedType == setType,
                        label = setTypeLabel,
                        onClick = { selectedType = if (selectedType == setType) null else setType },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = null,
                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                            )
                        },
                        modifier = Modifier.padding(horizontal = 4.dp),
                    )
                }
            }
        }
    }
}

private fun String.toReadableSetTypeLabel(
    oldDelimiter: String = "_",
    newDelimiter: String = " ",
): String =
    split(oldDelimiter).joinToString(separator = newDelimiter) { prevStr -> prevStr.capitalize() }
