package com.donghanx.design.composable.provider

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

val ProvidableCompositionLocal<SharedTransitionScope?>.currentNotNull: SharedTransitionScope
    @Composable get() = current ?: throw IllegalStateException("No SharedTransitionScope found")

val ProvidableCompositionLocal<AnimatedVisibilityScope?>.currentNotNull: AnimatedVisibilityScope
    @Composable get() = current ?: throw IllegalStateException("No AnimatedVisibilityScope found")
