package com.donghanx.randomcards.navigation

import androidx.navigation3.runtime.NavKey
import com.donghanx.design.composable.provider.NavAnimatedVisibilityProviderWrapper
import com.donghanx.model.CardPreview
import com.donghanx.navigation.NavKeyEntryProviderScope
import com.donghanx.randomcards.RandomCardsScreen
import kotlinx.serialization.Serializable

@Serializable data object RandomCardsRoute : NavKey

fun NavKeyEntryProviderScope.randomCardsEntry(
    onCardClick: (CardPreview) -> Unit,
    onScrollToTop: () -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
) {
    entry<RandomCardsRoute> {
        NavAnimatedVisibilityProviderWrapper {
            RandomCardsScreen(
                onCardClick = onCardClick,
                onScrollToTop = onScrollToTop,
                onShowSnackbar = onShowSnackbar,
            )
        }
    }
}
