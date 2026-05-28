package com.donghanx.search.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.metadata
import com.donghanx.design.animation.enterTransitionVertical
import com.donghanx.design.animation.exitTransitionVertical
import com.donghanx.model.SetInfo
import com.donghanx.navigation.NavKeyEntryProviderScope
import com.donghanx.navigation.Navigator
import com.donghanx.navigation.extensions.popTransition
import com.donghanx.navigation.extensions.transition
import com.donghanx.search.SearchScreen
import kotlinx.serialization.Serializable

@Serializable data object SearchRoute : NavKey

fun Navigator.navigateToSearch() {
    navigate(route = SearchRoute)
}

fun NavKeyEntryProviderScope.searchEntry(onCloseClick: () -> Unit, onSetClick: (SetInfo) -> Unit) {
    entry<SearchRoute>(
        metadata =
            metadata {
                transition {
                    enterTransitionVertical() togetherWith fadeOut(animationSpec = tween())
                }
                popTransition {
                    fadeIn(animationSpec = tween()) togetherWith exitTransitionVertical()
                }
            }
    ) {
        SearchScreen(onCloseClick = onCloseClick, onSetClick = onSetClick)
    }
}
