package com.donghanx.sets

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun SetInfoItem(code: String, name: String, iconUrl: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.horizontalScroll(state = rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AsyncImage(model = iconUrl, contentDescription = name, modifier = Modifier.size(24.dp))

        Text(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Text(text = code, fontSize = 14.sp, color = Color.DarkGray)
    }
}

@Composable
fun StickyYearReleased(yearReleased: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().background(color = Color.White)) {
        val yearReleasedStr = remember { yearReleased.toString() }
        Text(text = yearReleasedStr, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
private fun SetInfoItemPreview() {
    SetInfoItem(
        code = "soi",
        name = "Shadows over Innistrad",
        iconUrl = "https://svgs.scryfall.io/sets/soi.svg?1698638400"
    )
}

@Preview(showBackground = true)
@Composable
private fun StickyYearReleased() {
    StickyYearReleased(yearReleased = 2023)
}
