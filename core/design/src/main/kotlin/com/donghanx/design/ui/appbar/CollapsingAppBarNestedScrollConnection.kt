package com.donghanx.design.ui.appbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Stable
class CollapsingNestedScrollConnection(val maxHeight: Int) : NestedScrollConnection {

    private var _targetOffset by mutableFloatStateOf(0F)
    val targetOffset: Int
        get() = _targetOffset.roundToInt()

    val absoluteTargetOffset: Float
        get() = targetOffset.absoluteValue.toFloat()

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val delta = available.y
        val newOffset = _targetOffset + delta
        val previousOffset = _targetOffset

        _targetOffset = newOffset.coerceIn(-maxHeight.toFloat(), 0F)
        val consumed = _targetOffset - previousOffset
        return Offset(0F, consumed)
    }

    fun reset() {
        _targetOffset = 0F
    }
}

@Composable
fun rememberCollapsingNestedScrollConnection(maxHeight: Int) =
    remember(maxHeight) { CollapsingNestedScrollConnection(maxHeight = maxHeight) }
