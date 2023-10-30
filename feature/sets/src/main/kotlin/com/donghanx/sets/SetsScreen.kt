package com.donghanx.sets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SetsScreen(onShowSnackbar: suspend (message: String) -> Unit) {
    Text(text = "Sets")
}
