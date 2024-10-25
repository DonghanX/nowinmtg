package com.donghanx.setdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.donghanx.common.extensions.capitalize
import com.donghanx.design.R
import com.donghanx.design.ui.text.ResizableText
import com.donghanx.model.SetInfo

@Composable
fun SetDetailsScreen(onBackClick: () -> Unit, viewModel: SetDetailsViewModel = hiltViewModel()) {
    val setInfo by viewModel.setInfoFlow.collectAsStateWithLifecycle()
    Column {
        SetDetailsTopBar(setInfo = setInfo, onBackClick = onBackClick)
        setInfo?.let { SetDetailsBasicInfo(setInfo = it) }
    }
}

@Composable
private fun SetDetailsBasicInfo(setInfo: SetInfo, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(space = 6.dp)) {
        SuggestionChip(onClick = {}, label = { Text("${setInfo.cardCount} cards") })

        SuggestionChip(onClick = {}, label = { Text("Type: ${setInfo.setType.capitalize()}") })

        SuggestionChip(onClick = {}, label = { Text("Code: ${setInfo.code}") })

        SuggestionChip(onClick = {}, label = { Text("Released at ${setInfo.releasedAt}") })
    }
}

@Composable
private fun SetDetailsTopBar(
    setInfo: SetInfo?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().padding(bottom = 12.dp),
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.back),
            )
        }

        setInfo?.let {
            SetDetailsTitle(name = it.name, iconUri = it.iconSvgUri, modifier = Modifier.weight(1F))
        }

        Spacer(modifier = Modifier.size(40.dp))
    }
}

@Composable
private fun SetDetailsTitle(name: String, iconUri: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        AsyncImage(model = iconUri, contentDescription = name, modifier = Modifier.size(24.dp))

        Spacer(modifier = Modifier.width(4.dp))

        ResizableText(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}
