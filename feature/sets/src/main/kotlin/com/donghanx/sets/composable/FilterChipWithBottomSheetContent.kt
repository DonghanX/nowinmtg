package com.donghanx.sets.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FilterChipWithBottomSheetContent(
    label: @Composable () -> Unit,
    bottomSheetState: SheetState,
    selected: Boolean,
    showBottomSheet: Boolean,
    onShowBottomSheetChange: (Boolean) -> Unit,
    bottomSheetContent: @Composable () -> Unit,
) {
    FilterChip(label = label, selected = selected, onClick = { onShowBottomSheetChange(true) })

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onShowBottomSheetChange(false) },
            sheetState = bottomSheetState,
            contentWindowInsets = { WindowInsets.ime },
        ) {
            Box(modifier = Modifier.navigationBarsPadding()) { bottomSheetContent() }
        }
    }
}
