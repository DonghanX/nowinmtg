package com.donghanx.domain

import kotlinx.coroutines.flow.Flow

internal fun <T> mapValidCardIdFlow(
    cardId: String?,
    multiverseId: Int?,
    flowById: (cardId: String) -> Flow<T>,
    flowByMultiverseId: (multiverseId: Int) -> Flow<T>,
): Flow<T> =
    when {
        cardId != null -> flowById(cardId)
        multiverseId != null -> flowByMultiverseId(multiverseId)
        else ->
            throw IllegalArgumentException(
                "Both cardId and multiverseId are null; at least one must be provided."
            )
    }
