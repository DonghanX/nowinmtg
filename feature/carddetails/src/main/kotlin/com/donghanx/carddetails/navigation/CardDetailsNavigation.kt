package com.donghanx.carddetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.donghanx.carddetails.CardDetailsScreen

const val CARD_DETAILS_ROUTE = "CardDetails"
internal const val CARD_ID_ARGS = "CardId"

fun NavController.navigateToCardDetails(cardId: String) {
    navigate(route = "$CARD_DETAILS_ROUTE/$cardId") { launchSingleTop = true }
}

fun NavGraphBuilder.cardDetailsScreen(onShowSnackbar: suspend (message: String) -> Unit) {
    composable(route = "$CARD_DETAILS_ROUTE/{$CARD_ID_ARGS}") {
        CardDetailsScreen(onShowSnackbar = onShowSnackbar)
    }
}
