package com.donghanx.sets

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun SetInfoItemView(
    code: String,
    name: String,
    iconUrl: String,
) {
    Row(
        modifier =
            Modifier.fillMaxSize()
                .padding(horizontal = 6.dp)
                .horizontalScroll(state = rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AsyncImage(model = iconUrl, contentDescription = name, modifier = Modifier.size(24.dp))

        Text(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Text(text = code, fontSize = 14.sp, color = Color.DarkGray)
    }
}

@Preview(showBackground = true)
@Composable
private fun SetInfoItemPreview() {
    SetInfoItemView(
        code = "soi",
        name = "Shadows over Innistrad",
        iconUrl = "https://svgs.scryfall.io/sets/soi.svg?1698638400"
    )
}
