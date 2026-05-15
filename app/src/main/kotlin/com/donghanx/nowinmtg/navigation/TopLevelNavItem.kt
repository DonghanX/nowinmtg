package com.donghanx.nowinmtg.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import com.donghanx.design.R as DesignR
import com.donghanx.favorites.navigation.FavoritesRoute
import com.donghanx.randomcards.navigation.RandomCardsRoute
import com.donghanx.sets.navigation.SetsRoute

enum class TopLevelNavItem(
    val route: NavKey,
    @param:DrawableRes val selectedIconResId: Int,
    @param:DrawableRes val unselectedIconResId: Int,
    @param:StringRes val labelResId: Int,
) {
    RandomCards(
        route = RandomCardsRoute,
        selectedIconResId = DesignR.drawable.baseline_swipe_vertical_24,
        unselectedIconResId = DesignR.drawable.outline_swipe_vertical_24,
        labelResId = com.donghanx.randomcards.R.string.random_cards,
    ),
    Sets(
        route = SetsRoute,
        selectedIconResId = DesignR.drawable.baseline_dataset_24,
        unselectedIconResId = DesignR.drawable.outline_dataset_24,
        labelResId = com.donghanx.sets.R.string.sets,
    ),
    Favorites(
        route = FavoritesRoute,
        selectedIconResId = DesignR.drawable.baseline_favorite_24,
        unselectedIconResId = DesignR.drawable.outline_favorite_border_24,
        labelResId = com.donghanx.favorites.R.string.favorites,
    ),
}

val TOP_LEVEL_ROUTES = TopLevelNavItem.entries.map { it.route }.toSet()

/**
 * Remembers a map of [TopAppBarState] using [TopLevelNavItem] as key. This allows each
 * [com.donghanx.design.ui.appbar.NowInMtgTopAppBar] in top-level screens to preserve its own
 * independent scroll/collapse state when switching between tabs.
 */
@Composable
fun rememberTopAppBarStatesByTopLevelDestination(): Map<TopLevelNavItem, TopAppBarState> =
    remember {
        TopLevelNavItem.entries.associateWith {
            TopAppBarState(
                initialHeightOffsetLimit = -Float.MAX_VALUE,
                initialHeightOffset = 0f,
                initialContentOffset = 0f,
            )
        }
    }
