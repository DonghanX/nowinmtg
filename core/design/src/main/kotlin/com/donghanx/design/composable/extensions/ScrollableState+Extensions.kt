package com.donghanx.design.composable.extensions

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState

fun LazyListState.isFirstItemNotVisible(): Boolean = isItemNotVisible(index = 0)

fun LazyGridState.isFirstItemNotVisible(): Boolean = isItemNotVisible(index = 0)

fun LazyListState.isItemNotVisible(index: Int): Boolean = firstVisibleItemIndex > index

fun LazyGridState.isItemNotVisible(index: Int): Boolean = firstVisibleItemIndex > index
