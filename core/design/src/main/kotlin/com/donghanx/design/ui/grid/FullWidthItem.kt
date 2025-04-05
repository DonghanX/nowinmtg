package com.donghanx.design.ui.grid

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable

fun LazyGridScope.fullWidthItem(
    key: Any? = null,
    contentType: Any? = null,
    content: @Composable () -> Unit,
) {
    item(key = key, contentType = contentType, span = { GridItemSpan(maxLineSpan) }) { content() }
}
