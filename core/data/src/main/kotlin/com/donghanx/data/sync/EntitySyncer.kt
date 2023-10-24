package com.donghanx.data.sync

suspend fun <T, R> List<T>.syncWith(
    entityConverter: (T) -> R,
    modelActions: suspend (List<R>) -> Unit
) {
    map { networkEntity -> entityConverter(networkEntity) }
        .also { localEntity -> modelActions(localEntity) }
}

suspend fun <T, R> T.syncWith(entityConverter: (T) -> R, modelActions: suspend (R) -> Unit) {
    entityConverter(this).also { localEntity -> modelActions(localEntity) }
}
