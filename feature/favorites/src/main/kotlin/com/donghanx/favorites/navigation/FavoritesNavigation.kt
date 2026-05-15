package com.donghanx.favorites.navigation

import androidx.navigation3.runtime.NavKey
import com.donghanx.design.composable.provider.NavAnimatedVisibilityProviderWrapper
import com.donghanx.favorites.FavoritesScreen
import com.donghanx.model.CardPreview
import com.donghanx.navigation.NavKeyEntryProviderScope
import kotlinx.serialization.Serializable

@Serializable data object FavoritesRoute : NavKey

fun NavKeyEntryProviderScope.favoritesEntry(
    onCardClick: (CardPreview) -> Unit,
    onScrollToTop: () -> Unit,
) {
    entry<FavoritesRoute> {
        NavAnimatedVisibilityProviderWrapper {
            FavoritesScreen(onCardClick = onCardClick, onScrollToTop = onScrollToTop)
        }
    }
}
