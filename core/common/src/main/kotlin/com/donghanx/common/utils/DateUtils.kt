package com.donghanx.common.utils

import java.time.LocalDate
import java.time.ZoneOffset

/** Called to get the year integer from date string in the format of "yyyy-mm-dd" */
fun String.yearOfDate(): Int = toIsoLocalDate().year

fun String.epochMilliOfDate(offset: Int): Long {
    return toIsoLocalDate().toEpochMilli(offset)
}

private fun LocalDate.toEpochMilli(offset: Int): Long {
    return atStartOfDay().toInstant(offset.toZoneOffset()).toEpochMilli()
}

private fun String.toIsoLocalDate(): LocalDate {
    require(isNotEmpty())
    return LocalDate.parse(this)
}

private fun Int.toZoneOffset(): ZoneOffset {
    require(this >= 0)
    return ZoneOffset.of("+$this")
}

object DateUtils {
    val maxSupportedDateMillis = LocalDate.MAX.toEpochMilli(0)
}
