package com.donghanx.domain

import com.donghanx.common.NetworkResult
import com.donghanx.data.repository.sets.SetsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RefreshSetsUseCase @Inject constructor(private val setsRepository: SetsRepository) {

    suspend operator fun invoke(forceRefresh: Boolean = false): Flow<NetworkResult<Unit>> =
        if (setsRepository.shouldFetchInitialSets() || forceRefresh) {
            setsRepository.refreshAllSets()
        } else {
            flowOf(NetworkResult.Success(Unit))
        }
}
