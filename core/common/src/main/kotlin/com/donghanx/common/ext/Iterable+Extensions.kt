package com.donghanx.common.ext

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Iterable<T>.filterAll(vararg predicates: (T) -> Boolean): List<T> {
    return filter { element -> predicates.all { it(element) } }
}
