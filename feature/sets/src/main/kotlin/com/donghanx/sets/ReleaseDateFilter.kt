package com.donghanx.sets

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.donghanx.sets.composable.BottomSheetContentWrapper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReleaseDateFilter(
    onDateRangeSelected: (startDateMillis: Long?, endDateMillis: Long?) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    FilterChip(
        label = { Text(stringResource(R.string.release_date)) },
        selected = false,
        onClick = { showBottomSheet = true }
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState,
            windowInsets = WindowInsets.ime
        ) {
            ReleaseDatePicker(
                onDateRangeSelected = onDateRangeSelected,
                onHideBottomSheet = {
                    scope
                        .launch { bottomSheetState.hide() }
                        .invokeOnCompletion { showBottomSheet = false }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReleaseDatePicker(
    onDateRangeSelected: (startDateMillis: Long?, endDateMillis: Long?) -> Unit,
    onHideBottomSheet: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState(initialDisplayMode = DisplayMode.Input)

    BottomSheetContentWrapper(
        onViewResultsClick = {
            with(dateRangePickerState) {
                onDateRangeSelected(selectedStartDateMillis, selectedEndDateMillis)
            }
            onHideBottomSheet()
        },
        onResetClick = {
            onDateRangeSelected(null, null)
            onHideBottomSheet()
        }
    ) {
        DateRangePicker(state = dateRangePickerState, showModeToggle = false)
    }
}
