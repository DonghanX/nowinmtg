package com.donghanx.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>

    data class Error(val exception: Throwable?) : Result<Nothing>
}

fun <T> Flow<T>.asResultFlow(): Flow<Result<T>> =
    map<T, Result<T>> { Result.Success(it) }.catch { emit(Result.Error(it)) }

fun <T, R> Flow<Result<T>>.foldResult(
    onSuccess: (data: T) -> R,
    onError: (exception: Throwable?) -> R
): Flow<R> = map { result ->
    when (result) {
        is Result.Success -> onSuccess(result.data)
        is Result.Error -> onError(result.exception)
    }
}
