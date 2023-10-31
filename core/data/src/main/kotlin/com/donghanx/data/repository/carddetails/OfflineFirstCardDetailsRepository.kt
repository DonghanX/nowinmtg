package com.donghanx.data.repository.carddetails

import com.donghanx.common.NetworkResult
import com.donghanx.common.asResultFlow
import com.donghanx.common.foldResult
import com.donghanx.data.sync.syncWith
import com.donghanx.database.CardDetailsDao
import com.donghanx.database.model.asCardDetailsEntity
import com.donghanx.database.model.asExternalModel
import com.donghanx.model.CardDetails
import com.donghanx.model.NetworkCardDetails
import com.donghanx.network.MtgCardsRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class OfflineFirstCardDetailsRepository
@Inject
constructor(
    private val cardDetailsDao: CardDetailsDao,
    private val cardsRemoteDataSource: MtgCardsRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : CardDetailsRepository {
    override fun getCardDetailsById(cardId: String): Flow<CardDetails?> =
        cardDetailsDao.getCardDetailsById(cardId).map { it?.asExternalModel() }.flowOn(ioDispatcher)

    override fun refreshCardDetails(cardId: String): Flow<NetworkResult<Unit>> =
        flow { emit(cardsRemoteDataSource.getCardDetailsById(cardId)) }
            .asResultFlow()
            .foldResult(
                onSuccess = { cardDetails ->
                    cardDetails.syncWith(
                        entityConverter = NetworkCardDetails::asCardDetailsEntity,
                        modelActions = cardDetailsDao::upsertCardDetails
                    )
                    NetworkResult.Success(Unit)
                },
                onError = { NetworkResult.Error(it) }
            )
            .flowOn(ioDispatcher)
}
