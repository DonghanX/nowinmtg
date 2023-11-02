package com.donghanx.design.ui.scrolltotop

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.donghanx.design.R

@Composable
fun ScrollToTopButton(
    visible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.scroll_to_top)
) {
    AnimatedVisibility(visible = visible, modifier = modifier) {
        Button(onClick = onClick) { Text(text = text) }
    }
}

@Preview
@Composable
private fun ScrollToTopButtonPreview() {
    ScrollToTopButton(visible = true, onClick = {})
}
