package com.donghanx.design.composable.extensions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.material3.TopAppBarState

suspend fun TopAppBarState.animateReset(animationSpec: AnimationSpec<Float>? = null) {
    if (heightOffset == 0F) {
        contentOffset = 0F
        return
    }

    Animatable(heightOffset).animateTo(
        targetValue = 0F,
        animationSpec = animationSpec ?: tween(durationMillis = 200),
    ) {
        heightOffset = value
    }

    contentOffset = 0F
}
