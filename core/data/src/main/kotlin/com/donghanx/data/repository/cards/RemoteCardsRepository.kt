package com.donghanx.data.repository.cards

import com.donghanx.model.Card
import com.donghanx.network.di.MtgNetwork
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteCardsRepository
@Inject
constructor(
    private val mtgNetwork: MtgNetwork,
    private val dispatcher: CoroutineDispatcher
) : CardsRepository {

    override fun defaultCards(): Flow<List<Card>> =
        flow { emit(mtgNetwork.getCards()) }.flowOn(dispatcher)

    override fun randomCards(): Flow<List<Card>> =
        flow { emit(mtgNetwork.getRandomCards()) }.flowOn(dispatcher)
}
