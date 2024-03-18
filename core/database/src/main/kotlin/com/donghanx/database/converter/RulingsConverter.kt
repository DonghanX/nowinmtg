package com.donghanx.database.converter

import androidx.room.TypeConverter
import com.donghanx.model.network.Ruling
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RulingsConverter {
    @TypeConverter
    fun fromRulings(rulings: List<Ruling>): String {
        return Json.encodeToString(rulings)
    }

    @TypeConverter
    fun toRulings(string: String?): List<Ruling>? {
        return string?.let { Json.decodeFromString<List<Ruling>>(it) }
    }
}
