package com.donghanx.randomcards.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.donghanx.design.composable.provider.LocalNavAnimatedVisibilityScope
import com.donghanx.model.CardPreview
import com.donghanx.randomcards.RandomCardsScreen
import kotlinx.serialization.Serializable

@Serializable object RandomCardsBaseRoute

@Serializable object RandomCardsRoute

fun NavGraphBuilder.randomCardsGraph(
    onCardClick: (CardPreview) -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
    nestedGraph: NavGraphBuilder.() -> Unit,
) {
    navigation<RandomCardsBaseRoute>(startDestination = RandomCardsRoute) {
        composable<RandomCardsRoute> {
            CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
                RandomCardsScreen(onCardClick = onCardClick, onShowSnackbar = onShowSnackbar)
            }
        }

        nestedGraph()
    }
}
