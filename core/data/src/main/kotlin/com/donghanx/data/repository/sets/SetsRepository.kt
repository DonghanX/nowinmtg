package com.donghanx.data.repository.sets

import com.donghanx.common.NetworkResult
import com.donghanx.model.SetInfo
import kotlinx.coroutines.flow.Flow

interface SetsRepository {

    fun getAllSets(): Flow<List<SetInfo>>

    fun refreshAllSets(): Flow<NetworkResult<Unit>>

    suspend fun shouldFetchInitialSets(): Boolean
}
