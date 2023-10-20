package com.donghanx.database.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object StringListTypeConverters {
    @TypeConverter
    @JvmStatic
    fun toStringList(value: String?): List<String>? {
        return value?.let { Json.decodeFromString(value) }
    }

    @TypeConverter
    @JvmStatic
    fun fromStringList(strings: List<String>): String? {
        return Json.encodeToString(strings)
    }
}
