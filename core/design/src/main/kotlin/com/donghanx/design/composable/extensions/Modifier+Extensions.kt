package com.donghanx.design.composable.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.rippleClickable(onClick: () -> Unit): Modifier {
    return composed {
        clickable(
            onClick = onClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(),
        )
    }
}

@Composable
fun Modifier.conditional(
    condition: Boolean,
    modifier: @Composable Modifier.() -> Modifier,
): Modifier = if (condition) then(modifier(Modifier)) else this
