@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.donghanx.design.composable.provider

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

val ProvidableCompositionLocal<SharedTransitionScope?>.currentNotNull: SharedTransitionScope
    @Composable get() = current ?: throw IllegalStateException("No SharedTransitionScope found")

val ProvidableCompositionLocal<AnimatedVisibilityScope?>.currentNotNull: AnimatedVisibilityScope
    @Composable get() = current ?: throw IllegalStateException("No AnimatedVisibilityScope found")

@Composable
fun SharedTransitionProviderWrapper(content: @Composable () -> Unit) {
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
