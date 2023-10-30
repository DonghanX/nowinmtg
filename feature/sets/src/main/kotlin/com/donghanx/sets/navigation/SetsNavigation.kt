package com.donghanx.sets.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.donghanx.sets.SetsScreen

const val SETS_ROUTE = "Sets"

fun NavGraphBuilder.setsScreen(onShowSnackbar: suspend (message: String) -> Unit) {
    composable(route = SETS_ROUTE) { SetsScreen(onShowSnackbar = onShowSnackbar) }
}
