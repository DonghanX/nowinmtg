package com.donghanx.carddetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.donghanx.carddetails.CardDetailsScreen

const val CARD_DETAILS_ROUTE = "CardDetails"

fun NavController.navigateToCardDetails(navOptions: NavOptions? = null) {
    navigate(route = CARD_DETAILS_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.cardDetailsScreen(onShowSnackbar: suspend (message: String) -> Unit) {
    composable(route = CARD_DETAILS_ROUTE) { CardDetailsScreen(onShowSnackbar = onShowSnackbar) }
}
