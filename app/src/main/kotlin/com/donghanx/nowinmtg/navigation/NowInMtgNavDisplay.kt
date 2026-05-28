package com.donghanx.nowinmtg.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import com.donghanx.carddetails.navigation.cardDetailsEntry
import com.donghanx.carddetails.navigation.navigateToCardDetails
import com.donghanx.carddetails.navigation.navigateToCardDetailsWithMultiverseId
import com.donghanx.common.INVALID_ID
import com.donghanx.design.animation.enterTransitionBetweenNavEntries
import com.donghanx.design.animation.exitTransitionBetweenNavEntries
import com.donghanx.favorites.navigation.FavoritesRoute
import com.donghanx.favorites.navigation.favoritesEntry
import com.donghanx.navigation.NavigationState
import com.donghanx.navigation.Navigator
import com.donghanx.navigation.toDecoratedEntries
import com.donghanx.randomcards.navigation.RandomCardsRoute
import com.donghanx.randomcards.navigation.randomCardsEntry
import com.donghanx.search.navigation.SearchRoute
import com.donghanx.search.navigation.searchEntry
import com.donghanx.setdetails.navigation.SetDetailsRoute
import com.donghanx.setdetails.navigation.navigateToSetDetails
import com.donghanx.setdetails.navigation.setDetailsEntry
import com.donghanx.sets.navigation.SetsRoute
import com.donghanx.sets.navigation.setsEntry
import com.donghanx.settings.navigation.settingsDialogEntry

@Composable
fun NowInMtgNavDisplay(
    navigator: Navigator,
    navigationState: NavigationState,
    onScrollToTop: () -> Unit,
    onTopBarVisibilityChanged: (isCollapsed: Boolean) -> Unit,
    onShowSnackbar: suspend (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dialogStrategy = remember { DialogSceneStrategy<NavKey>() }

    val entryProvider = entryProvider {
        randomCardsEntry(
            onCardClick = { card ->
                navigator.navigateToCardDetailsWithMultiverseId(
                    previewImageUrl = card.imageUrl,
                    multiverseId = card.id.toIntOrNull() ?: INVALID_ID,
                    parentRoute = RandomCardsRoute.toString(),
                )
            },
            onScrollToTop = onScrollToTop,
            onShowSnackbar = onShowSnackbar,
        )
        setsEntry(
            onShowSnackbar = onShowSnackbar,
            onSetClick = { setInfo ->
                navigator.navigateToSetDetails(
                    setId = setInfo.scryfallId,
                    parentRoute = SetsRoute.toString(),
                )
            },
            onScrollToTop = onScrollToTop,
        )
        favoritesEntry(
            onCardClick = { card ->
                navigator.navigateToCardDetails(
                    previewImageUrl = card.imageUrl,
                    cardId = card.id,
                    parentRoute = FavoritesRoute.toString(),
                )
            },
            onScrollToTop = onScrollToTop,
        )

        searchEntry(
            onCloseClick = navigator::goBack,
            onSetClick = { setInfo ->
                navigator.navigateToSetDetails(
                    setId = setInfo.scryfallId,
                    parentRoute = SearchRoute.toString(),
                )
            },
        )
        setDetailsEntry(
            onBackClick = navigator::goBack,
            onCardClick = { card ->
                navigator.navigateToCardDetails(
                    cardId = card.id,
                    previewImageUrl = card.imageUrl,
                    parentRoute = SetDetailsRoute.toString(),
                )
            },
            onTopBarVisibilityChanged = onTopBarVisibilityChanged,
            onShowSnackbar = onShowSnackbar,
        )

        cardDetailsEntry(onBackClick = navigator::goBack, onShowSnackbar = onShowSnackbar)

        settingsDialogEntry()
    }

    NavDisplay(
        entries = navigationState.toDecoratedEntries(entryProvider),
        onBack = { navigator.goBack() },
        sceneStrategies = listOf(dialogStrategy),
        modifier = modifier,
        transitionSpec = { enterTransitionBetweenNavEntries() },
        popTransitionSpec = { exitTransitionBetweenNavEntries() },
    )
}
