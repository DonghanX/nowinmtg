package com.donghanx.database.converter

import androidx.room.TypeConverter
import com.donghanx.model.network.NetworkRuling
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RulingsConverter {
    @TypeConverter
    fun fromRulings(rulings: List<NetworkRuling>): String {
        return Json.encodeToString(rulings)
    }

    @TypeConverter
    fun toRulings(string: String?): List<NetworkRuling>? {
        return string?.let { Json.decodeFromString<List<NetworkRuling>>(it) }
    }
}
