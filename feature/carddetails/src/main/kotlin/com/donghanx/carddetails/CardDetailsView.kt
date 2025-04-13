@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.donghanx.carddetails

import androidx.compose.animation.ExperimentalSharedTransitionApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.placeholder
import com.donghanx.common.extensions.capitalize
import com.donghanx.design.composable.provider.LocalNavAnimatedVisibilityScope
import com.donghanx.design.composable.provider.LocalSharedTransitionScope
import com.donghanx.design.composable.provider.SharedTransitionProviderWrapper
import com.donghanx.design.composable.provider.currentNotNull
import com.donghanx.design.ui.card.ExpandableCard
import com.donghanx.design.ui.shared.CardSharedElementKey
import com.donghanx.mock.MockUtils
import com.donghanx.model.CardDetails
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
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            // TODO: Accommodate different window size
            CardImage(
                imageUrl = cardDetails?.imageUris?.png ?: previewImageUrl,
                cacheKeyId = cacheKeyId,
                contentDescription = cardDetails?.name,
                parentRoute = parentRoute,
                placeholderResId = placeholderResId,
            )

            cardDetails?.let { CardBasicInfo(cardDetails = it, modifier = modifier.fillMaxWidth()) }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                    .then(
                        if (cacheKey.isValid())
                            Modifier.sharedElement(
                                state = rememberSharedContentState(key = cacheKey),
                                animatedVisibilityScope =
                                    LocalNavAnimatedVisibilityScope.currentNotNull,
                            )
                        else Modifier
                    )
                    .fillMaxWidth(fraction = 0.5F)
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = cardDetails.name,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "${cardDetails.setName} (${cardDetails.set})",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )

            LightHorizontalDivider()

            Text(
                text = cardDetails.typeLine,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )

            LightHorizontalDivider()

            if (!cardDetails.power.isNullOrEmpty() && !cardDetails.toughness.isNullOrEmpty()) {
                Text(
                    text = "${cardDetails.power}/${cardDetails.toughness}",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
                LightHorizontalDivider()
            }

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

                    LightHorizontalDivider()
                }

            Text(
                text = cardDetails.rarity.capitalize(),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )

            LightHorizontalDivider()

            cardDetails.artist?.let { artist ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.illustrated_by),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )

                    HorizontalDivider(modifier = Modifier.width(2.dp))

                    Text(
                        text = artist,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Medium,
                    )
                }
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
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        cardDetails.text?.let { cardText ->
            ExpandableCard(headerTitle = stringResource(id = R.string.card_text)) {
                Text(text = cardText, textAlign = TextAlign.Start, fontWeight = FontWeight.Normal)
            }
        }

        cardDetails.flavor?.let { cardFlavor ->
            ExpandableCard(headerTitle = stringResource(id = R.string.flavor)) {
                Text(
                    text = cardFlavor,
                    textAlign = TextAlign.Start,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium,
                )
            }
        }

        rulings
            .takeIf { it.isNotEmpty() }
            ?.let { rulings ->
                ExpandableCard(headerTitle = stringResource(id = R.string.rulings)) {
                    CardRulings(rulings = rulings)
                }
            }
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
private fun LightHorizontalDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(modifier = modifier, thickness = 0.5.dp)
}

@Preview(showBackground = true)
@Composable
private fun CardDetailsViewPreview(
    @PreviewParameter(CardDetailsPreviewParameterProvider::class) cardDetails: CardDetails
) {
    SharedTransitionProviderWrapper {
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

class CardDetailsPreviewParameterProvider : PreviewParameterProvider<CardDetails> {
    override val values: Sequence<CardDetails>
        get() = sequenceOf(MockUtils.cardDetailsProgenitus, MockUtils.cardDetailsIncomplete)
}
