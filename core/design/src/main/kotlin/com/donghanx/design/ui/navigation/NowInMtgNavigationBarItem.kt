package com.donghanx.design.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable

@Composable
fun RowScope.NowInMtgNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    selectedIcon: @Composable () -> Unit,
    unSelectedIcon: @Composable () -> Unit,
    label: @Composable () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else unSelectedIcon,
        label = label,
    )
}

@Composable
fun NowInMtgNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    selectedIcon: @Composable () -> Unit,
    unSelectedIcon: @Composable () -> Unit,
    label: @Composable () -> Unit,
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else unSelectedIcon,
        label = label,
    )
}
