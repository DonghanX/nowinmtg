package com.donghanx.setdetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SetDetailsScreen(viewModel: SetDetailsViewModel = hiltViewModel()) {
    val setCode by viewModel.setCode.collectAsStateWithLifecycle()
    setCode?.let { Text("set details for $it") }
}
