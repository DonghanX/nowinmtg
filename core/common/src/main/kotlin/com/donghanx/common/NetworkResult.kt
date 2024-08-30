package com.donghanx.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>

    data class Error(val exception: Throwable?) : NetworkResult<Nothing>
}

fun <T> Flow<T>.asResultFlow(): Flow<NetworkResult<T>> =
    map<T, NetworkResult<T>> { NetworkResult.Success(it) }.catch { emit(NetworkResult.Error(it)) }

fun <T, R> Flow<NetworkResult<T>>.foldResult(
    onSuccess: suspend (data: T) -> R,
    onError: suspend (exception: Throwable?) -> R,
): Flow<R> = map { result ->
    when (result) {
        is NetworkResult.Success -> onSuccess(result.data)
        is NetworkResult.Error -> onError(result.exception)
    }
}
