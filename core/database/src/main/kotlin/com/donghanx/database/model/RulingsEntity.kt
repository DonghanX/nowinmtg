package com.donghanx.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.donghanx.model.Ruling
import com.donghanx.model.network.NetworkRuling

@Entity(tableName = "rulings")
data class RulingsEntity(
    @PrimaryKey(autoGenerate = true) val rulingId: Long = 0L,
    val cardId: String,
    val comment: String,
    val publishedAt: String,
    val source: String,
)

fun NetworkRuling.asRulingsEntity(cardId: String): RulingsEntity =
    RulingsEntity(comment = comment, cardId = cardId, publishedAt = publishedAt, source = source)

fun RulingsEntity.asExternalModel(): Ruling =
    Ruling(comment = comment, publishedAt = publishedAt, source = source)
