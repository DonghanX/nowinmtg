package com.donghanx.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.donghanx.common.extensions.toDisplayName
import com.donghanx.common.utils.monthYearOfDate
import com.donghanx.design.theme.NowInMTGTheme
import com.donghanx.mock.MockUtils
import com.donghanx.model.SetInfo

@Composable
fun SetInfoItem(
    setInfo: SetInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    maxLines: Int = 2,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 4.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = setInfo.iconSvgUri,
            contentDescription = setInfo.name,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.size(36.dp),
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1F)) {
            with(setInfo) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(Modifier.height(2.dp))

                SetMetaDataRow(
                    code = code,
                    setType = setType,
                    cardCount = cardCount,
                    releasedAt = releasedAt,
                )
            }
        }
    }
}

@Composable
private fun SetMetaDataRow(
    code: String,
    setType: String,
    cardCount: Int,
    releasedAt: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        SetCodeChip(code)

        val cardCount = pluralStringResource(R.plurals.card_count, cardCount, cardCount)

        Text(
            text = "${setType.toDisplayName()} · $cardCount · ${releasedAt.monthYearOfDate()}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun SetCodeChip(code: String, modifier: Modifier = Modifier) {
    Text(
        text = code.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier =
            modifier
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(horizontal = 6.dp, vertical = 4.dp),
    )
}

@Composable
fun StickyYearReleased(yearReleased: Int, count: Int, modifier: Modifier = Modifier) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(vertical = 6.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = yearReleased.toString(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        HorizontalDivider(
            modifier = Modifier.weight(1F).padding(horizontal = 10.dp),
            color = MaterialTheme.colorScheme.outlineVariant,
        )

        Text(
            text = pluralStringResource(R.plurals.set_count, count, count),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SetInfoItemPreview() {
    NowInMTGTheme {
        SetInfoItem(
            setInfo = MockUtils.soiExpansion,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StickyYearReleasedPreview() {
    NowInMTGTheme { StickyYearReleased(yearReleased = 2023, count = 10) }
}
