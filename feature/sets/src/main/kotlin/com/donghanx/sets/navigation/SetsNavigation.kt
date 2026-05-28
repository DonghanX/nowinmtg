package com.donghanx.sets.navigation

import androidx.navigation3.runtime.NavKey
import com.donghanx.model.SetInfo
import com.donghanx.navigation.NavKeyEntryProviderScope
import com.donghanx.sets.SetsScreen
import kotlinx.serialization.Serializable

@Serializable data object SetsRoute : NavKey

fun NavKeyEntryProviderScope.setsEntry(
    onSetClick: (SetInfo) -> Unit,
    onScrollToTop: () -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
) {
    entry<SetsRoute> {
        SetsScreen(
            onShowSnackbar = onShowSnackbar,
            onSetClick = onSetClick,
            onScrollToTop = onScrollToTop,
        )
    }
}
