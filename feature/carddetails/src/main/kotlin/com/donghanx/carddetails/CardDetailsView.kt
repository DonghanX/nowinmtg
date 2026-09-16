package com.donghanx.carddetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.placeholder
import com.donghanx.common.extensions.capitalize
import com.donghanx.design.composable.extensions.conditional
import com.donghanx.design.composable.provider.LocalNavAnimatedVisibilityScope
import com.donghanx.design.composable.provider.LocalSharedTransitionScope
import com.donghanx.design.composable.provider.SharedTransitionProviderPreviewWrapper
import com.donghanx.design.composable.provider.currentNotNull
import com.donghanx.design.theme.NowInMTGTheme
import com.donghanx.design.theme.RarityTheme
import com.donghanx.design.theme.fromRarity
import com.donghanx.design.ui.expandable.ExpandableSection
import com.donghanx.design.ui.shared.CardSharedElementKey
import com.donghanx.mock.MockUtils
import com.donghanx.model.CardDetails
import com.donghanx.model.Rarity
import com.donghanx.model.Ruling

@Composable
internal fun CardDetailsView(
    cardDetails: CardDetails?,
    rulings: List<Ruling>,
    previewImageUrl: String?,
    cacheKeyId: String?,
    parentRoute: String,
    modifier: Modifier = Modifier,
    placeholderResId: Int? = null,
) {
    Column(modifier = modifier.fillMaxSize().padding(horizontal = 8.dp)) {
        CardImage(
            imageUrl = cardDetails?.imageUris?.png ?: previewImageUrl,
            cacheKeyId = cacheKeyId,
            contentDescription = cardDetails?.name,
            parentRoute = parentRoute,
            placeholderResId = placeholderResId,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.height(16.dp))

        cardDetails?.let { CardBasicInfo(cardDetails = it, modifier = modifier.fillMaxWidth()) }

        Spacer(modifier = Modifier.height(8.dp))

        cardDetails?.let { CardDescription(cardDetails = it, rulings = rulings) }
    }
}

@Composable
private fun CardImage(
    cacheKeyId: String?,
    imageUrl: String?,
    contentDescription: String?,
    parentRoute: String,
    modifier: Modifier = Modifier,
    placeholderResId: Int? = null,
) {
    with(LocalSharedTransitionScope.currentNotNull) {
        val cacheKey =
            remember(cacheKeyId) { CardSharedElementKey(id = cacheKeyId, origin = parentRoute) }
        AsyncImage(
            model =
                ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .placeholderMemoryCacheKey(cacheKey.toMemoryCacheKey())
                    .apply { placeholderResId?.let { placeholder(it) } }
                    .build(),
            contentDescription = contentDescription,
            modifier =
                modifier
                    .conditional(cacheKey.isValid()) {
                        sharedElement(
                            sharedContentState = rememberSharedContentState(key = cacheKey),
                            animatedVisibilityScope = LocalNavAnimatedVisibilityScope.currentNotNull,
                        )
                    }
                    .fillMaxWidth(fraction = 0.8F)
                    .aspectRatio(ratio = 5F / 7F),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun CardBasicInfo(cardDetails: CardDetails, modifier: Modifier = Modifier) {
    SelectionContainer(modifier = modifier) {
        Column(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = cardDetails.name,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall,
                )

                // TODO: parse manaCost string to a visualized form
                cardDetails.manaCost
                    ?.takeIf { it.isNotEmpty() }
                    ?.let { manaCost ->
                        Text(
                            text = manaCost,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                cardDetails.typeLine?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

                cardDetails.toStatBadgeText()?.let { CardStatsBadge(stats = it) }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = cardDetails.toCardCollectorInfo(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                CardRarityBadge(rarity = cardDetails.rarity)
            }
        }
    }
}

@Composable
private fun CardDescription(
    cardDetails: CardDetails,
    rulings: List<Ruling>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SectionDivider()

        cardDetails.text?.let { cardText ->
            Text(
                text = cardText,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        cardDetails.flavor?.let { cardFlavor ->
            Text(
                text = cardFlavor,
                textAlign = TextAlign.Start,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        cardDetails.artist?.let { artist -> CardArtistRow(artist) }

        if (rulings.isNotEmpty()) {
            SectionDivider()

            ExpandableSection(headerTitle = stringResource(id = R.string.rulings)) {
                CardRulings(rulings = rulings)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun CardArtistRow(artist: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Create,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(14.dp),
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = stringResource(id = R.string.illustrated_by),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = artist,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontStyle = FontStyle.Italic,
        )
    }
}

@Composable
private fun CardRulings(rulings: List<Ruling>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.heightIn(max = 500.dp)) {
        itemsIndexed(rulings) { index, ruling ->
            Text(
                text =
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                            append(ruling.publishedAt)
                        }
                        append(": ${ruling.comment}")
                        if (index != rulings.lastIndex) append('\n')
                    },
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Composable
private fun CardRarityBadge(rarity: Rarity, modifier: Modifier = Modifier) {
    val rarityColors = RarityTheme.colors.fromRarity(rarity)

    Text(
        text = rarity.name.capitalize(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.SemiBold,
        color = rarityColors.onColorContainer,
        modifier =
            modifier
                .clip(RoundedCornerShape(6.dp))
                .background(rarityColors.colorContainer)
                .padding(horizontal = 6.dp, vertical = 2.dp),
    )
}

@Composable
private fun CardStatsBadge(stats: String, modifier: Modifier = Modifier) {
    Text(
        text = stats,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier =
            modifier
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(horizontal = 6.dp, vertical = 2.dp),
    )
}

@Composable
private fun SectionDivider() {
    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), thickness = 1.dp)
}

@PreviewLightDark
@Composable
private fun CardDetailsViewPreview(
    @PreviewParameter(CardDetailsPreviewParameterProvider::class) cardDetails: CardDetails
) {
    SharedTransitionProviderPreviewWrapper {
        NowInMTGTheme {
            CardDetailsView(
                cacheKeyId = null,
                cardDetails = cardDetails,
                rulings = MockUtils.rulingsProgenitus,
                parentRoute = "Favorites",
                previewImageUrl = null,
                placeholderResId = R.drawable.img_progenitus,
            )
        }
    }
}

class CardDetailsPreviewParameterProvider : PreviewParameterProvider<CardDetails> {
    override val values: Sequence<CardDetails>
        get() = sequenceOf(MockUtils.cardDetailsProgenitus, MockUtils.cardDetailsIncomplete)
}

private fun CardDetails.toStatBadgeText(): String? =
    when {
        power != null && toughness != null -> "$power/$toughness"
        loyalty != null -> loyalty
        defense != null -> defense
        else -> null
    }

private fun CardDetails.toCardCollectorInfo(): String =
    "$setName (${set.uppercase()}) · #${collectorNumber}"
