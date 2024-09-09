package com.donghanx.domain

import com.donghanx.common.NetworkResult
import com.donghanx.data.repository.carddetails.CardDetailsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class RefreshCardDetailsUseCase
@Inject
constructor(private val cardDetailsRepository: CardDetailsRepository) {

    operator fun invoke(
        cardIdFlow: StateFlow<String?>,
        multiverseIdFlow: StateFlow<Int>,
    ): Flow<NetworkResult<Unit>> =
        flatMapValidCardIdFlow(
            cardIdFlow = cardIdFlow,
            multiverseIdFlow = multiverseIdFlow,
            flatMapById = cardDetailsRepository::refreshCardDetailsById,
            flatMapByMultiverseId = cardDetailsRepository::refreshCardDetailsByMultiverseId,
        )
}
