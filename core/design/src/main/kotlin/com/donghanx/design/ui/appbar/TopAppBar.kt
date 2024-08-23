package com.donghanx.design.ui.appbar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowInMtgTopAppBar(
    @StringRes titleResId: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    showNavigationIcon: Boolean,
    shouldAdjustNavigationRail: Boolean,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationIconClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(titleResId), fontWeight = FontWeight.Bold) },
        colors = colors,
        navigationIcon = {
            Row {
                if (showNavigationIcon) {
                    if (shouldAdjustNavigationRail) Spacer(modifier = Modifier.width(width = 16.dp))

                    IconButton(onClick = onNavigationIconClick) {
                        Icon(
                            imageVector = navigationIcon,
                            contentDescription = navigationIconContentDescription
                        )
                    }
                }
            }
        },
        modifier = modifier
    )
}
