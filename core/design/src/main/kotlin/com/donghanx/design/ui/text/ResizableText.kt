package com.donghanx.design.ui.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun ResizableText(
    text: String,
    modifier: Modifier = Modifier,
    resizable: Boolean = true,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
) {
    var multiplier by remember { mutableFloatStateOf(1F) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        maxLines = maxLines,
        fontSize = fontSize * multiplier,
        fontWeight = fontWeight,
        onTextLayout = {
            if (resizable && it.hasVisualOverflow) {
                multiplier *= 0.9F
            } else {
                readyToDraw = true
            }
        },
        modifier =
            modifier.drawWithContent {
                if (readyToDraw) {
                    drawContent()
                }
            },
    )
}
