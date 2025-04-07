package com.donghanx.nowinmtg.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.donghanx.design.R as DesignR
import com.donghanx.favorites.navigation.FavoritesBaseRoute
import com.donghanx.favorites.navigation.FavoritesRoute
import com.donghanx.randomcards.navigation.RandomCardsBaseRoute
import com.donghanx.randomcards.navigation.RandomCardsRoute
import com.donghanx.sets.navigation.SetsBaseRoute
import com.donghanx.sets.navigation.SetsRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
    @DrawableRes val selectedIconResId: Int,
    @DrawableRes val unselectedIconResId: Int,
    @StringRes val labelResId: Int,
) {
    RandomCards(
        route = RandomCardsRoute::class,
        baseRoute = RandomCardsBaseRoute::class,
        selectedIconResId = DesignR.drawable.baseline_swipe_vertical_24,
        unselectedIconResId = DesignR.drawable.outline_swipe_vertical_24,
        labelResId = com.donghanx.randomcards.R.string.random_cards,
    ),
    Sets(
        route = SetsRoute::class,
        baseRoute = SetsBaseRoute::class,
        selectedIconResId = DesignR.drawable.baseline_dataset_24,
        unselectedIconResId = DesignR.drawable.outline_dataset_24,
        labelResId = com.donghanx.sets.R.string.sets,
    ),
    Favorites(
        route = FavoritesRoute::class,
        baseRoute = FavoritesBaseRoute::class,
        selectedIconResId = DesignR.drawable.baseline_favorite_24,
        unselectedIconResId = DesignR.drawable.outline_favorite_border_24,
        labelResId = com.donghanx.favorites.R.string.favorites,
    ),
}
