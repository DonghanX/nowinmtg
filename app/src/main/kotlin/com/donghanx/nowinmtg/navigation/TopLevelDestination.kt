package com.donghanx.nowinmtg.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.donghanx.design.R as DesignR
import com.donghanx.favorites.navigation.FAVORITES_GRAPH_ROUTE
import com.donghanx.randomcards.navigation.RANDOM_CARDS_GRAPH_ROUTE
import com.donghanx.sets.navigation.SETS_ROUTE

sealed class TopLevelDestination(
    val route: String,
    @DrawableRes val selectedIconResId: Int,
    @DrawableRes val unselectedIconResId: Int,
    @StringRes val labelResId: Int,
) {
    data object RandomCards :
        TopLevelDestination(
            route = RANDOM_CARDS_GRAPH_ROUTE,
            selectedIconResId = DesignR.drawable.baseline_swipe_vertical_24,
            unselectedIconResId = DesignR.drawable.outline_swipe_vertical_24,
            labelResId = com.donghanx.randomcards.R.string.random_cards,
        )

    data object Sets :
        TopLevelDestination(
            route = SETS_ROUTE,
            selectedIconResId = DesignR.drawable.baseline_dataset_24,
            unselectedIconResId = DesignR.drawable.outline_dataset_24,
            labelResId = com.donghanx.sets.R.string.sets,
        )

    data object Favorites :
        TopLevelDestination(
            route = FAVORITES_GRAPH_ROUTE,
            selectedIconResId = DesignR.drawable.baseline_favorite_24,
            unselectedIconResId = DesignR.drawable.outline_favorite_border_24,
            labelResId = com.donghanx.favorites.R.string.favorites,
        )
}
