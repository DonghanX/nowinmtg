package com.donghanx.navigation

import androidx.navigation3.runtime.NavKey

/**
 * Handle navigation events (forward and back) by updating [NavigationState].
 *
 * NavDisplay observes [NavigationState] and re-renders its UI to react to any state changes.
 */
class Navigator(val state: NavigationState) {

    fun navigate(route: NavKey) {
        with(state) {
            when (route) {
                topLevelRoute -> clearCurrentBackStack()
                // This is a top-level route, just switch to it.
                in backStacks.keys -> updateTopLevelRoute(route)
                else -> navigateToRoute(route)
            }
        }
    }

    fun goBack() {
        with(state) {
            // If we're at the base of the current route, go back to the previous top-level stack.
            if (currentRoute == topLevelRoute) {
                topLevelRouteStack.removeLastOrNull()
            } else {
                currentStack.removeLastOrNull()
            }
        }
    }

    private fun navigateToRoute(route: NavKey) {
        with(state.currentStack) {
            remove(route)
            add(route)
        }
    }

    private fun updateTopLevelRoute(route: NavKey) {
        with(state.topLevelRouteStack) {
            if (route == state.startRoute) {
                // Clear the topLevelRouteStack to contain only its start route, so goBack() can
                // return users to the device's home screen.
                clear()
            } else {
                remove(route)
            }
            add(route)
        }
    }

    private fun clearCurrentBackStack() {
        with(state.currentStack) { if (size > 1) subList(1, size).clear() }
    }
}
