package com.donghanx.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import com.donghanx.navigation.navkey.DialogNavKey

/** Create a navigation state that persists config changes and process death. */
@Composable
fun rememberNavigationState(startRoute: NavKey, topLevelRoutes: Set<NavKey>): NavigationState {
    require(startRoute in topLevelRoutes) { "startRoute must be included in topLevelRoutes" }
    val topLevelRouteStack = rememberNavBackStack(startRoute)
    val backStacks = topLevelRoutes.associateWith { key -> rememberNavBackStack(key) }

    return remember(startRoute, topLevelRoutes) {
        NavigationState(
            startRoute = startRoute,
            topLevelRouteStack = topLevelRouteStack,
            backStacks = backStacks,
        )
    }
}

/**
 * State holder for navigation state. This class does not modify its own state. It is designed to be
 * modified using the `Navigator` class.
 *
 * @param startRoute the start route. The user will exit the app through this route.
 * @param topLevelRouteStack the back stack that tracks all the active top-level route.
 * @param backStacks the back stacks for each top-level route.
 */
class NavigationState(
    val startRoute: NavKey,
    val topLevelRouteStack: NavBackStack<NavKey>,
    val backStacks: Map<NavKey, NavBackStack<NavKey>>,
) {
    val topLevelRoute: NavKey by derivedStateOf { topLevelRouteStack.last() }

    val currentStack: NavBackStack<NavKey>
        get() = backStacks[topLevelRoute] ?: error("Stack for $topLevelRoute not found")

    val currentRoute: NavKey
        get() = currentStack.last()

    val currentRouteIgnoringDialog: NavKey
        get() = currentStack.last { it !is DialogNavKey }
}

@Composable
fun NavigationState.toDecoratedEntries(
    entryProvider: (NavKey) -> NavEntry<NavKey>
): List<NavEntry<NavKey>> {
    // For each backstack, create a SaveableStateHolder decorator and ViewModelStore decorator and
    // use them to decorate the entries from that stack. When backStacks changes,
    // rememberDecoratedNavEntries will be recomposed and a new list of decorated entries is
    // returned. ViewModels should remain alive even when a top-level route is not active as long as
    // the corresponding route is in the backstack.
    val decoratedEntries = backStacks.mapValues { (_, stack) ->
        val decorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
                // TODO: once lifecycle-viewmodel-nav3 2.11.0 beta2 is available, check the new
                //  rememberViewModelStoreNavEntryDecorator overload that accepts the
                //  ViewModelStoreProvider to fix this issue:
                //  https://issuetracker.google.com/issues/503420425
                //  For now, use 2.10.0 stable version instead.
                rememberViewModelStoreNavEntryDecorator(),
            )

        rememberDecoratedNavEntries(
            backStack = stack,
            entryDecorators = decorators,
            entryProvider = entryProvider,
        )
    }

    return topLevelRouteStack.flatMap { decoratedEntries[it].orEmpty() }
}
