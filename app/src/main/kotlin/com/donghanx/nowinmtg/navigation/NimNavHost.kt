package com.donghanx.nowinmtg.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.donghanx.carddetails.navigation.cardDetailsScreen
import com.donghanx.carddetails.navigation.navigateToCardDetails
import com.donghanx.carddetails.navigation.navigateToCardDetailsWithMultiverseId
import com.donghanx.favorites.navigation.favoriteGraph
import com.donghanx.randomcards.navigation.RANDOM_CARDS_GRAPH_ROUTE
import com.donghanx.randomcards.navigation.randomCardsGraph
import com.donghanx.search.navigation.searchScreen
import com.donghanx.sets.navigation.setsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NimNavHost(
    navController: NavHostController,
    onShowSnackbar: suspend (String) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = RANDOM_CARDS_GRAPH_ROUTE,
) {
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
        ) {
            randomCardsGraph(
                onCardClick = { index, multiverseId, parentRoute ->
                    navController.navigateToCardDetailsWithMultiverseId(
                        index = index,
                        multiverseId = multiverseId,
                        parentRoute = parentRoute,
                    )
                },
                onShowSnackbar = onShowSnackbar,
                sharedTransitionScope = this@SharedTransitionLayout,
                nestedGraphs = { from ->
                    cardDetailsScreen(
                        parentRoute = from,
                        onBackClick = navController::popBackStack,
                        onShowSnackbar = onShowSnackbar,
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                },
            )
            setsScreen(onShowSnackbar = onShowSnackbar)
            searchScreen(onCloseClick = navController::popBackStack)
            favoriteGraph(
                onCardClick = { index, cardId, parentRoute ->
                    navController.navigateToCardDetails(
                        index = index,
                        cardId = cardId,
                        parentRoute = parentRoute,
                    )
                },
                nestedGraphs = { parentRoute ->
                    cardDetailsScreen(
                        parentRoute = parentRoute,
                        onBackClick = navController::popBackStack,
                        onShowSnackbar = onShowSnackbar,
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                },
                sharedTransitionScope = this@SharedTransitionLayout,
            )
        }
    }
}
