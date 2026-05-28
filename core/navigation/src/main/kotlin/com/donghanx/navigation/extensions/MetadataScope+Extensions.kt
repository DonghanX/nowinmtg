package com.donghanx.navigation.extensions

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.navigation3.runtime.MetadataScope
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.NavDisplay

fun MetadataScope.transition(transformProvider: SceneTransitionScope.() -> ContentTransform) {
    put(NavDisplay.TransitionKey, transformProvider)
}

fun MetadataScope.popTransition(transformProvider: SceneTransitionScope.() -> ContentTransform) {
    put(NavDisplay.PopTransitionKey, transformProvider)
}

private typealias SceneTransitionScope = AnimatedContentTransitionScope<Scene<*>>
