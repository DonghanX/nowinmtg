package com.donghanx.data.sync

suspend fun <T, R> List<T>.syncListWith(
    entityConverter: (T) -> R?,
    modelActions: suspend (List<R>) -> Unit,
) {
    mapNotNull { networkEntity -> entityConverter(networkEntity) }
        .also { localEntity -> modelActions(localEntity) }
}

suspend fun <T, R> List<T>.syncListIndexedWith(
    entityConverter: (index: Int, networkEntity: T) -> R?,
    modelActions: suspend (List<R>) -> Unit,
) {
    mapIndexedNotNull { index, networkEntity -> entityConverter(index, networkEntity) }
        .also { localEntity -> modelActions(localEntity) }
}

suspend fun <T, R> T.syncWith(entityConverter: (T) -> R, modelActions: suspend (R) -> Unit) {
    entityConverter(this).also { localEntity -> modelActions(localEntity) }
}
