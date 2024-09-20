package com.donghanx.sets.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.donghanx.model.SetInfo
import com.donghanx.sets.SetsScreen

const val SETS_GRAPH_ROUTE = "SetsGraph"
const val SETS_ROUTE = "Sets"

fun NavGraphBuilder.setsScreen(
    onSetClick: (SetInfo) -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
    nestedGraphs: NavGraphBuilder.(parentRoute: String) -> Unit,
) {
    navigation(startDestination = SETS_ROUTE, route = SETS_GRAPH_ROUTE) {
        composable(route = SETS_ROUTE) {
            SetsScreen(onShowSnackbar = onShowSnackbar, onSetClick = onSetClick)
        }

        nestedGraphs(SETS_ROUTE)
    }
}
