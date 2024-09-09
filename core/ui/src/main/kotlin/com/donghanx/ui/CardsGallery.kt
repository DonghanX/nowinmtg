package com.donghanx.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Precision
import com.donghanx.design.R
import com.donghanx.design.composable.extensions.isFirstItemNotVisible
import com.donghanx.design.composable.extensions.rippleClickable
import com.donghanx.design.ui.scrolltotop.ScrollToTopButton
import com.donghanx.model.CardPreview
import kotlinx.coroutines.launch

@Composable
fun CardsGallery(
    cards: List<CardPreview>,
    onCardClick: (CardPreview) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        val scope = rememberCoroutineScope()
        val lazyGridState = rememberLazyGridState()

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            state = lazyGridState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(all = 4.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(cards, key = { it.id }) { card ->
                AsyncImage(
                    model =
                        ImageRequest.Builder(LocalContext.current)
                            .data(card.imageUrl)
                            .precision(Precision.EXACT)
                            .crossfade(true)
                            .build(),
                    contentDescription = card.name,
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(id = R.drawable.blank_card_placeholder),
                    modifier =
                        Modifier.fillMaxWidth()
                            .wrapContentHeight()
                            .rippleClickable(onClick = { onCardClick(card) }),
                )
            }
        }

        val shouldShowScrollToTopButton by remember {
            derivedStateOf { lazyGridState.isFirstItemNotVisible() }
        }
        ScrollToTopButton(
            visible = shouldShowScrollToTopButton,
            onClick = { scope.launch { lazyGridState.animateScrollToItem(0) } },
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}
