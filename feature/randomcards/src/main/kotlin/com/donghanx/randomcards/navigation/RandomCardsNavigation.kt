package com.donghanx.randomcards.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.donghanx.randomcards.RandomCardsScreen

const val RANDOM_CARDS_ROUTE = "RandomCards"

fun NavController.navigateToRandomCards(navOptions: NavOptions? = null) {
    this.navigate(route = RANDOM_CARDS_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.randomCardsScreen(
    onShowSnackbar: suspend (message: String) -> Unit,
) {
    composable(route = RANDOM_CARDS_ROUTE) { RandomCardsScreen(onShowSnackbar = onShowSnackbar) }
}
