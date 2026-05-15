package com.donghanx.design.composable.provider

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation3.ui.LocalNavAnimatedContentScope

@Composable
fun NotInMtgProvidersWrapper(
    isBottomNavBarAnimating: Boolean,
    content: @Composable SharedTransitionScope.() -> Unit,
) {
    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this@SharedTransitionLayout,
            LocalIsBottomNavBarAnimating provides isBottomNavBarAnimating,
        ) {
            content()
        }
    }
}

@Composable
fun NavAnimatedVisibilityProviderWrapper(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalNavAnimatedVisibilityScope provides LocalNavAnimatedContentScope.current
    ) {
        content()
    }
}

@Composable
fun SharedTransitionProviderPreviewWrapper(content: @Composable () -> Unit) {
    SharedTransitionLayout {
        AnimatedVisibility(visible = true) {
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this@SharedTransitionLayout,
                LocalNavAnimatedVisibilityScope provides this@AnimatedVisibility,
            ) {
                content()
            }
        }
    }
}
