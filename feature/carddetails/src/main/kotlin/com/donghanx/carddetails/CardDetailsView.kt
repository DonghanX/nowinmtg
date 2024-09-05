package com.donghanx.carddetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.donghanx.design.R as DesignR
import com.donghanx.design.ui.card.ExpandableCard
import com.donghanx.mock.MockUtils
import com.donghanx.model.CardDetails

@Composable
internal fun CardDetailsView(cardDetails: CardDetails, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(horizontal = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            // TODO: Accommodate different window size
            cardDetails.imageUris?.let { imageUris ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(imageUris.png).build(),
                    contentDescription = cardDetails.name,
                    modifier = Modifier.weight(0.5F).aspectRatio(ratio = 5F / 7F),
                    placeholder = painterResource(id = DesignR.drawable.blank_card_placeholder),
                    contentScale = ContentScale.Crop,
                )
            }

            CardBasicInfo(cardDetails, modifier.weight(weight = 0.5F))
        }

        Spacer(modifier = Modifier.height(16.dp))

        CardDescription(cardDetails)
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
                fontWeight = FontWeight.Medium,
            )
            Divider()

            Text(text = cardDetails.typeLine, textAlign = TextAlign.Center, fontSize = 16.sp)
            Divider()

            if (!cardDetails.power.isNullOrEmpty() && !cardDetails.toughness.isNullOrEmpty()) {
                Text(
                    text = "${cardDetails.power}/${cardDetails.toughness}",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                )
                Divider()
            }

            // TODO: parse manaCost string to a visualized form
            cardDetails.manaCost?.let { manaCost ->
                Text(text = manaCost, textAlign = TextAlign.Center, fontSize = 16.sp)
                Divider()
            }

            Text(text = cardDetails.rarity, textAlign = TextAlign.Center, fontSize = 16.sp)
            Divider()

            cardDetails.artist?.let { artist ->
                Column {
                    Text(
                        text = stringResource(id = R.string.illustrated_by),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                    )
                    Text(
                        text = artist,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }
        }
    }
}

@Composable
private fun CardDescription(cardDetails: CardDetails, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        cardDetails.text?.let { cardText ->
            ExpandableCard(headerTitle = stringResource(id = R.string.card_text)) {
                Text(text = cardText, textAlign = TextAlign.Start, fontWeight = FontWeight.Medium)
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

        //        cardDetails.rulings?.let { rulings ->
        //            ExpandableCard(headerTitle = stringResource(id = R.string.rulings)) {
        //                CardRulings(rulings = rulings)
        //            }
        //        }
    }
}

// @Composable
// private fun CardRulings(rulings: List<Ruling>, modifier: Modifier = Modifier) {
//    LazyColumn(modifier = modifier.heightIn(max = 500.dp)) {
//        itemsIndexed(rulings) { index, currRuling ->
//            Text(
//                text =
//                    buildAnnotatedString {
//                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
//                            append(currRuling.date)
//                        }
//                        append(": ${currRuling.text}")
//                        if (index != rulings.lastIndex) append('\n')
//                    },
//                textAlign = TextAlign.Start,
//            )
//        }
//    }
// }

@Preview(showBackground = true)
@Composable
private fun CardDetailsViewPreview(
    @PreviewParameter(CardDetailsPreviewParameterProvider::class) cardDetails: CardDetails
) {
    CardDetailsView(cardDetails = cardDetails)
}

class CardDetailsPreviewParameterProvider : PreviewParameterProvider<CardDetails> {
    override val values: Sequence<CardDetails>
        get() = sequenceOf(MockUtils.cardDetailsProgenitus, MockUtils.cardDetailsIncomplete)
}
