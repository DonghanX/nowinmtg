package com.donghanx.nowinmtg.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.donghanx.design.R
import com.donghanx.randomcards.navigation.RANDOM_CARDS_ROUTE
import com.donghanx.sets.navigation.SETS_ROUTE

sealed class TopLevelDestination(
    val route: String,
    @DrawableRes val iconResId: Int,
    @StringRes val labelResId: Int
) {
    data object RandomCards :
        TopLevelDestination(
            route = RANDOM_CARDS_ROUTE,
            iconResId = R.drawable.baseline_swipe_vertical_24,
            labelResId = com.donghanx.randomcards.R.string.random_cards
        )

    data object Sets :
        TopLevelDestination(
            route = SETS_ROUTE,
            iconResId = R.drawable.baseline_dataset_24,
            labelResId = com.donghanx.sets.R.string.sets
        )
}
