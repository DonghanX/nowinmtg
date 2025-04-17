package com.donghanx.nowinmtg.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.donghanx.carddetails.navigation.cardDetailsScreen
import com.donghanx.carddetails.navigation.navigateToCardDetails
import com.donghanx.carddetails.navigation.navigateToCardDetailsWithMultiverseId
import com.donghanx.common.INVALID_ID
import com.donghanx.design.composable.provider.LocalSharedTransitionScope
import com.donghanx.favorites.navigation.FavoritesRoute
import com.donghanx.favorites.navigation.favoriteGraph
import com.donghanx.randomcards.navigation.RandomCardsBaseRoute
import com.donghanx.randomcards.navigation.RandomCardsRoute
import com.donghanx.randomcards.navigation.randomCardsGraph
import com.donghanx.search.navigation.SearchRoute
import com.donghanx.search.navigation.searchScreen
import com.donghanx.setdetails.navigation.SetDetailsRoute
import com.donghanx.setdetails.navigation.navigateToSetDetails
import com.donghanx.setdetails.navigation.setDetailsScreen
import com.donghanx.sets.navigation.SetsRoute
import com.donghanx.sets.navigation.setsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NimNavHost(
    navController: NavHostController,
    onShowSnackbar: suspend (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SharedTransitionLayout {
        CompositionLocalProvider(LocalSharedTransitionScope provides this) {
            NavHost(
                navController = navController,
                startDestination = RandomCardsBaseRoute,
                modifier = modifier,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) },
            ) {
                randomCardsGraph(
                    onCardClick = { card ->
                        navController.navigateToCardDetailsWithMultiverseId(
                            previewImageUrl = card.imageUrl,
                            multiverseId = card.id.toIntOrNull() ?: INVALID_ID,
                            parentRoute = RandomCardsRoute.toString(),
                        )
                    },
                    onShowSnackbar = onShowSnackbar,
                    nestedGraph = {
                        cardDetailsScreen(
                            onBackClick = navController::popBackStack,
                            onShowSnackbar = onShowSnackbar,
                        )
                    },
                )

                setsScreen(
                    onShowSnackbar = onShowSnackbar,
                    onSetClick = { setInfo ->
                        navController.navigateToSetDetails(
                            setId = setInfo.scryfallId,
                            parentRoute = SetsRoute.toString(),
                        )
                    },
                    nestedGraphs = {
                        setDetailsScreen(
                            onBackClick = navController::popBackStack,
                            onCardClick = { card ->
                                navController.navigateToCardDetails(
                                    cardId = card.id,
                                    previewImageUrl = card.imageUrl,
                                    parentRoute = SetDetailsRoute.toString(),
                                )
                            },
                            onShowSnackbar = onShowSnackbar,
                        )

                        cardDetailsScreen(
                            onBackClick = navController::popBackStack,
                            onShowSnackbar = onShowSnackbar,
                        )
                    },
                )

                searchScreen(
                    onCloseClick = navController::popBackStack,
                    onSetClick = { setInfo ->
                        navController.navigateToSetDetails(
                            setId = setInfo.scryfallId,
                            parentRoute = SearchRoute.toString(),
                        )
                    },
                )

                favoriteGraph(
                    onCardClick = { card ->
                        navController.navigateToCardDetails(
                            previewImageUrl = card.imageUrl,
                            cardId = card.id,
                            parentRoute = FavoritesRoute.toString(),
                        )
                    },
                    nestedGraph = {
                        cardDetailsScreen(
                            onBackClick = navController::popBackStack,
                            onShowSnackbar = onShowSnackbar,
                        )
                    },
                )
            }
        }
    }
}
