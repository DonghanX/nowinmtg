package com.donghanx.data.repository.cards

import com.donghanx.common.NetworkResult
import com.donghanx.common.asResultFlow
import com.donghanx.common.foldResult
import com.donghanx.data.sync.syncWith
import com.donghanx.database.RandomCardsDao
import com.donghanx.database.model.RandomCardEntity
import com.donghanx.database.model.asExternalModel
import com.donghanx.database.model.asRandomCardEntity
import com.donghanx.model.CardPreview
import com.donghanx.model.network.NetworkCard
import com.donghanx.network.MtgCardsRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class OfflineFirstRandomCardsRepository
@Inject
constructor(
    private val randomCardsDao: RandomCardsDao,
    private val cardsRemoteDataSource: MtgCardsRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : RandomCardsRepository {
    override fun getRandomCards(): Flow<List<CardPreview>> =
        randomCardsDao
            .getRandomCards()
            .map { it.map(RandomCardEntity::asExternalModel) }
            .flowOn(ioDispatcher)

    override fun refreshRandomCards(): Flow<NetworkResult<Unit>> =
        flow { emit(cardsRemoteDataSource.getRandomCards()) }
            .asResultFlow()
            .foldResult(
                onSuccess = { randomCards ->
                    randomCards.syncWith(
                        entityConverter = NetworkCard::asRandomCardEntity,
                        modelActions = randomCardsDao::deleteAllAndInsertRandomCards,
                    )

                    NetworkResult.Success(Unit)
                },
                onError = { NetworkResult.Error(it) },
            )
            .flowOn(ioDispatcher)

    override suspend fun shouldFetchInitialCards(): Boolean =
        withContext(ioDispatcher) { randomCardsDao.getRandomCardsCount() == 0 }
}
