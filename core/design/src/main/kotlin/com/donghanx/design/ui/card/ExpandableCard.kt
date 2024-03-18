package com.donghanx.design.ui.card

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableCard(
    headerTitle: String,
    modifier: Modifier = Modifier,
    expandedContent: @Composable () -> Unit
) {
    val (expanded, setExpanded) = remember { mutableStateOf(true) }

    Card(
        modifier =
            modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 8.dp).clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
                setExpanded(!expanded)
            }
    ) {
        AnimatedContent(
            targetState = expanded,
            modifier = Modifier.padding(all = 8.dp),
            label = "Expandable Card"
        ) { expanded ->
            if (!expanded)
                Row {
                    Text(text = headerTitle, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                }
            else expandedContent()
        }
    }
}
