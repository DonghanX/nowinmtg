package com.donghanx.data.repository.setdetails

import com.donghanx.common.NetworkResult
import com.donghanx.common.asResultFlow
import com.donghanx.common.foldResult
import com.donghanx.data.sync.syncListWith
import com.donghanx.database.CardDetailsDao
import com.donghanx.database.model.CardDetailsEntity
import com.donghanx.database.model.asCardDetailsEntity
import com.donghanx.database.model.asExternalModel
import com.donghanx.database.model.asExternalPreviewModel
import com.donghanx.model.CardDetails
import com.donghanx.model.CardPreview
import com.donghanx.model.network.NetworkCardDetails
import com.donghanx.network.SetsRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class OfflineFirstSetDetailsRepository
@Inject
constructor(
    private val setsRemoteDataSource: SetsRemoteDataSource,
    private val cardDetailsDao: CardDetailsDao,
    private val ioDispatcher: CoroutineDispatcher,
) : SetDetailsRepository {

    override fun refreshCardsInCurrentSet(searchUri: String): Flow<NetworkResult<Unit>> =
        flow {
                var nextPageUri: String? = searchUri
                val cardsInSet = mutableListOf<NetworkCardDetails>()

                while (!nextPageUri.isNullOrBlank()) {
                    with(setsRemoteDataSource.getCardsInCurrentSet(nextPageUri)) {
                        cardsInSet.addAll(cards)
                        nextPageUri = if (hasMore) nextPage else null
                    }
                }

                emit(cardsInSet)
            }
            .asResultFlow()
            .foldResult(
                onSuccess = { cards ->
                    cards.syncListWith(
                        entityConverter = NetworkCardDetails::asCardDetailsEntity,
                        modelActions = cardDetailsDao::upsertCardDetailsList,
                    )
                    NetworkResult.Success(Unit)
                },
                onError = { NetworkResult.Error(it) },
            )
            .flowOn(ioDispatcher)

    override fun getCardsInCurrentSet(setCode: String): Flow<List<CardPreview>> =
        cardDetailsDao
            .getCardDetailsListBySetCode(setCode)
            .map { it.map(CardDetailsEntity::asExternalPreviewModel) }
            .flowOn(ioDispatcher)
}
