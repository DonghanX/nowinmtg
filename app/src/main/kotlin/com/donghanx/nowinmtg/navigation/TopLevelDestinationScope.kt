package com.donghanx.nowinmtg.navigation

import androidx.compose.runtime.Composable
import com.donghanx.nowinmtg.ui.NowInMtgAppState

/** A Composable scope that runs the [content] only when the current destination is at top level */
@Composable
fun TopLevelDestinationScope(
    appState: NowInMtgAppState,
    content: @Composable (TopLevelDestination) -> Unit
) {
    appState.currentTopLevelDestination?.let { topLevelDestination -> content(topLevelDestination) }
}
