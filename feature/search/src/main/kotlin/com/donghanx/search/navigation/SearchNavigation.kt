package com.donghanx.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.donghanx.search.SearchScreen

const val SEARCH_ROUTE = "Search"

fun NavController.navigateToSearch() {
    navigate(route = SEARCH_ROUTE) { launchSingleTop = true }
}

fun NavGraphBuilder.searchScreen(onShowSnackbar: suspend (message: String) -> Unit) {
    composable(route = SEARCH_ROUTE) { SearchScreen(onShowSnackbar = onShowSnackbar) }
}
