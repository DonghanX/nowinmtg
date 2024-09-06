package com.donghanx.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.donghanx.model.Ruling
import com.donghanx.model.network.NetworkRuling

@Entity(tableName = "rulings")
data class RulingsEntity(@PrimaryKey val cardId: String, val rulings: List<NetworkRuling>)

fun List<NetworkRuling>.asRulingsEntity(cardId: String): RulingsEntity =
    RulingsEntity(cardId = cardId, rulings = this)

fun RulingsEntity.asExternalModel(): List<Ruling> =
    rulings.map { Ruling(comment = it.comment, it.publishedAt, it.source) }
