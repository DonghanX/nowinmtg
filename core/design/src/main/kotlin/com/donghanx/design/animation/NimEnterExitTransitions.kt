package com.donghanx.design.animation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith

private const val DEFAULT_DURATION_MILLIS = 300

fun enterTransitionHorizontal(durationMillis: Int = DEFAULT_DURATION_MILLIS): EnterTransition =
    fadeInTween(durationMillis) +
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(durationMillis = durationMillis),
        )

fun enterTransitionVertical(durationMillis: Int = DEFAULT_DURATION_MILLIS): EnterTransition =
    fadeInTween(durationMillis) +
        slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = durationMillis),
        )

fun exitTransitionHorizontal(durationMillis: Int = DEFAULT_DURATION_MILLIS): ExitTransition =
    fadeOutTween(durationMillis) +
        slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(durationMillis = durationMillis),
        )

fun exitTransitionVertical(durationMillis: Int = DEFAULT_DURATION_MILLIS): ExitTransition =
    fadeOutTween(durationMillis) +
        slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = durationMillis),
        )

fun enterTransitionBetweenNavEntries(
    durationMillis: Int = DEFAULT_DURATION_MILLIS
): ContentTransform = fadeInTween(durationMillis) togetherWith fadeOutTween(durationMillis)

fun exitTransitionBetweenNavEntries(
    durationMillis: Int = DEFAULT_DURATION_MILLIS
): ContentTransform = fadeInTween(durationMillis) togetherWith fadeOutTween(durationMillis)

fun fadeInTween(durationMillis: Int = DEFAULT_DURATION_MILLIS) =
    fadeIn(animationSpec = tween(durationMillis = durationMillis))

fun fadeOutTween(durationMillis: Int = DEFAULT_DURATION_MILLIS) =
    fadeOut(animationSpec = tween(durationMillis = durationMillis))
