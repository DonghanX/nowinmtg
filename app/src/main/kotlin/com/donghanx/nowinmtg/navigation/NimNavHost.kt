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
import com.donghanx.favorites.navigation.favoriteGraph
import com.donghanx.randomcards.navigation.RANDOM_CARDS_GRAPH_ROUTE
import com.donghanx.randomcards.navigation.randomCardsGraph
import com.donghanx.search.navigation.SEARCH_ROUTE
import com.donghanx.search.navigation.searchScreen
import com.donghanx.setdetails.navigation.navigateToSetDetails
import com.donghanx.setdetails.navigation.setDetailsScreen
import com.donghanx.sets.navigation.SETS_ROUTE
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
        CompositionLocalProvider(LocalSharedTransitionScope provides this) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = modifier,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) },
            ) {
                randomCardsGraph(
                    onCardClick = { card, parentRoute ->
                        navController.navigateToCardDetailsWithMultiverseId(
                            previewImageUrl = card.imageUrl,
                            multiverseId = card.id.toIntOrNull() ?: INVALID_ID,
                            parentRoute = parentRoute,
                        )
                    },
                    onShowSnackbar = onShowSnackbar,
                )

                setsScreen(
                    onShowSnackbar = onShowSnackbar,
                    onSetClick = { setInfo ->
                        navController.navigateToSetDetails(
                            setId = setInfo.scryfallId,
                            parentRoute = SETS_ROUTE,
                        )
                    },
                    nestedGraphs = { parentRoute ->
                        setDetailsScreen(
                            parentRoute = parentRoute,
                            onBackClick = navController::popBackStack,
                            onCardClick = { card ->
                                navController.navigateToCardDetails(
                                    cardId = card.id,
                                    previewImageUrl = card.imageUrl,
                                    parentRoute = SETS_ROUTE,
                                )
                            },
                        )
                    },
                )

                searchScreen(
                    onCloseClick = navController::popBackStack,
                    onSetClick = { setInfo ->
                        navController.navigateToSetDetails(
                            setId = setInfo.scryfallId,
                            parentRoute = SEARCH_ROUTE,
                        )
                    },
                )

                cardDetailsScreen(
                    onBackClick = navController::popBackStack,
                    onShowSnackbar = onShowSnackbar,
                )

                favoriteGraph(
                    onCardClick = { card, parentRoute ->
                        navController.navigateToCardDetails(
                            previewImageUrl = card.imageUrl,
                            cardId = card.id,
                            parentRoute = parentRoute,
                        )
                    }
                )
            }
        }
    }
}
