package com.donghanx.nowinmtg.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier,
    startDestination: String = RANDOM_CARDS_GRAPH_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
    ) {
        randomCardsGraph(
            onCardClick = { cacheKey, multiverseId, parentRoute ->
                navController.navigateToCardDetailsWithMultiverseId(
                    imageCacheKey = cacheKey,
                    multiverseId = multiverseId,
                    parentRoute = parentRoute,
                )
            },
            onShowSnackbar = onShowSnackbar,
            sharedTransitionScope = sharedTransitionScope,
            nestedGraphs = { from ->
                cardDetailsScreen(
                    parentRoute = from,
                    onBackClick = navController::popBackStack,
                    onShowSnackbar = onShowSnackbar,
                    sharedTransitionScope = sharedTransitionScope,
                )
            },
        )
        setsScreen(onShowSnackbar = onShowSnackbar)
        searchScreen(onCloseClick = navController::popBackStack)
        favoriteGraph(
            onCardClick = { cacheKey, cardId, parentRoute ->
                navController.navigateToCardDetails(
                    imageCacheKey = cacheKey,
                    cardId = cardId,
                    parentRoute = parentRoute,
                )
            },
            nestedGraphs = { parentRoute ->
                cardDetailsScreen(
                    parentRoute = parentRoute,
                    onBackClick = navController::popBackStack,
                    onShowSnackbar = onShowSnackbar,
                    sharedTransitionScope = sharedTransitionScope,
                )
            },
            sharedTransitionScope = sharedTransitionScope,
        )
    }
}
