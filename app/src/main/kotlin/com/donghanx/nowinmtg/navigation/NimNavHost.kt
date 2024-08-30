package com.donghanx.nowinmtg.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.donghanx.carddetails.navigation.cardDetailsScreen
import com.donghanx.carddetails.navigation.navigateToCardDetails
import com.donghanx.favorites.navigation.favoriteGraph
import com.donghanx.randomcards.navigation.RANDOM_CARDS_GRAPH_ROUTE
import com.donghanx.randomcards.navigation.randomCardsGraph
import com.donghanx.search.navigation.searchScreen
import com.donghanx.sets.navigation.setsScreen

@Composable
fun NimNavHost(
    navController: NavHostController,
    onShowSnackbar: suspend (String) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = RANDOM_CARDS_GRAPH_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        randomCardsGraph(
            onCardClick = { cardId, parentRoute ->
                navController.navigateToCardDetails(cardId = cardId, parentRoute = parentRoute)
            },
            onShowSnackbar = onShowSnackbar,
            nestedGraphs = { from ->
                cardDetailsScreen(
                    parentRoute = from,
                    onBackClick = navController::popBackStack,
                    onShowSnackbar = onShowSnackbar,
                )
            },
        )
        setsScreen(onShowSnackbar = onShowSnackbar)
        searchScreen(onCloseClick = navController::popBackStack)
        favoriteGraph(
            onCardClick = { cardId, parentRoute ->
                navController.navigateToCardDetails(cardId = cardId, parentRoute = parentRoute)
            },
            nestedGraphs = { parentRoute ->
                cardDetailsScreen(
                    parentRoute = parentRoute,
                    onBackClick = navController::popBackStack,
                    onShowSnackbar = onShowSnackbar,
                )
            },
        )
    }
}
