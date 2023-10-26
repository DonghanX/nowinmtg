package com.donghanx.nowinmtg.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.donghanx.carddetails.navigation.cardDetailsScreen
import com.donghanx.carddetails.navigation.navigateToCardDetails
import com.donghanx.design.R as DesignR
import com.donghanx.randomcards.R as RandomCardsR
import com.donghanx.randomcards.navigation.RANDOM_CARDS_ROUTE
import com.donghanx.randomcards.navigation.randomCardsScreen

@Composable
fun NimNavHost(
    navController: NavHostController,
    onShowSnackbar: suspend (String) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = RANDOM_CARDS_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        randomCardsScreen(
            onCardClick = { cardId -> navController.navigateToCardDetails(cardId = cardId) },
            onShowSnackbar = onShowSnackbar
        )
        cardDetailsScreen(onShowSnackbar = onShowSnackbar)
    }
}

sealed class TopLevelDestination(
    val route: String,
    @DrawableRes val iconResId: Int,
    @StringRes val labelResId: Int
) {
    data object RandomCards :
        TopLevelDestination(
            route = RANDOM_CARDS_ROUTE,
            iconResId = DesignR.drawable.baseline_swipe_vertical_24,
            labelResId = RandomCardsR.string.random_cards
        )
}
