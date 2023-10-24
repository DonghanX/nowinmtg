package com.donghanx.carddetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.donghanx.design.R
import com.donghanx.mock.MockUtils
import com.donghanx.model.CardDetails

@Composable
fun CardDetailsView(cardDetails: CardDetails, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(horizontal = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            AsyncImage(
                model =
                    ImageRequest.Builder(LocalContext.current).data(cardDetails.imageUrl).build(),
                contentDescription = cardDetails.name,
                modifier = Modifier.weight(0.5F).aspectRatio(ratio = 5F / 7F),
                placeholder = painterResource(id = R.drawable.blank_card_placeholder),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(0.5F),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = cardDetails.name, textAlign = TextAlign.Center)
                Text(text = cardDetails.set)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardDetailsViewPreview(
    @PreviewParameter(CardDetailsPreviewParameterProvider::class) cardDetails: CardDetails
) {
    CardDetailsView(cardDetails = cardDetails)
}

class CardDetailsPreviewParameterProvider : PreviewParameterProvider<CardDetails> {
    override val values: Sequence<CardDetails>
        get() = sequenceOf(MockUtils.cardDetailsAvacyn, MockUtils.cardDetailsIncomplete)
}
