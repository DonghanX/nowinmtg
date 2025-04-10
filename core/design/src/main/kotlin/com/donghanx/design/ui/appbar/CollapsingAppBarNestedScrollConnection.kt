package com.donghanx.design.ui.appbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource

@Stable
class CollapsingNestedScrollConnection(val maxHeight: Int) : NestedScrollConnection {

    var targetOffset: Int by mutableIntStateOf(0)
        private set

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val delta = available.y.toInt()
        val newOffset = targetOffset + delta
        val previousOffset = targetOffset

        targetOffset = newOffset.coerceIn(-maxHeight, 0)
        return Offset(0f, (targetOffset - previousOffset).toFloat())
    }
}

@Composable
fun rememberCollapsingNestedScrollConnection(maxHeight: Int) =
    remember(maxHeight) { CollapsingNestedScrollConnection(maxHeight = maxHeight) }
