package com.donghanx.sets

import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.donghanx.common.utils.DateMillisRange
import com.donghanx.common.utils.epochMilliOfDate
import com.donghanx.common.utils.fromEpochMilliseconds
import com.donghanx.sets.composable.BottomSheetContentWrapper
import com.donghanx.sets.composable.FilterChipWithBottomSheetContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReleaseDateFilter(
    selectedDateMillisRange: DateMillisRange,
    onDateRangeSelected: (startDateMillis: Long?, endDateMillis: Long?) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val (showBottomSheet, onShowBottomSheetChange) = remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    FilterChipWithBottomSheetContent(
        label = { Text(text = selectedDateMillisRange.toReleaseDateFilterChipLabel()) },
        bottomSheetState = bottomSheetState,
        selected = selectedDateMillisRange.isNotEmpty(),
        showBottomSheet = showBottomSheet,
        onShowBottomSheetChange = onShowBottomSheetChange
    ) {
        ReleaseDatePicker(
            initialDateMillisRange = selectedDateMillisRange,
            onDateRangeSelected = onDateRangeSelected,
            onHideBottomSheet = {
                scope
                    .launch { bottomSheetState.hide() }
                    .invokeOnCompletion { onShowBottomSheetChange(false) }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReleaseDatePicker(
    initialDateMillisRange: DateMillisRange,
    onDateRangeSelected: (startDateMillis: Long?, endDateMillis: Long?) -> Unit,
    onHideBottomSheet: () -> Unit
) {

    val dateRangePickerState =
        rememberDateRangePickerState(
            initialDisplayMode = DisplayMode.Input,
            initialSelectedStartDateMillis =
                remember { initialDateMillisRange.startDateMillisOrDefault() },
            initialSelectedEndDateMillis = initialDateMillisRange.endMillis
        )

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

/**
 * Return the default start date millis (the release date of the first MTG set) if the
 * [DateMillisRange.endMillis] is provided and the [DateMillisRange.startMillis] is null. Otherwise
 * return the original [DateMillisRange.startMillis].
 *
 * The default start date millis helps accommodate the input requirement of [DateRangePicker] , as
 * [DateRangePicker] throws an exception when an end date is provided without a start date,
 */
private fun DateMillisRange.startDateMillisOrDefault(): Long? {
    return if (hasEndMillisOnly()) "1993-08-05".epochMilliOfDate(RELEASE_DATE_OFFSET)
    else startMillis
}

@Composable
private fun DateMillisRange.toReleaseDateFilterChipLabel(): String {
    val startDateIfExists = startMillis?.fromEpochMilliseconds(RELEASE_DATE_OFFSET)
    val endDateIfExists = endMillis?.fromEpochMilliseconds(RELEASE_DATE_OFFSET)

    return when {
        isEmpty() -> stringResource(id = R.string.release_date)
        hasStartMillisOnly() -> "from $startDateIfExists"
        hasEndMillisOnly() -> "until $endDateIfExists"
        else -> "from $startDateIfExists to $endDateIfExists"
    }
}
