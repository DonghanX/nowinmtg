package com.donghanx.domain

import com.donghanx.common.NetworkResult
import com.donghanx.data.repository.carddetails.CardDetailsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class RefreshCardDetailsUseCase
@Inject
constructor(private val cardDetailsRepository: CardDetailsRepository) {

    operator fun invoke(cardId: String?, multiverseId: Int?): Flow<NetworkResult<Unit>> =
        mapValidCardIdFlow(
            cardId = cardId,
            multiverseId = multiverseId,
            flowById = cardDetailsRepository::refreshCardDetailsById,
            flowByMultiverseId = cardDetailsRepository::refreshCardDetailsByMultiverseId,
        )
}
