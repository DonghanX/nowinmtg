package com.donghanx.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.donghanx.model.CardPreview
import com.donghanx.model.NetworkCard

@Entity(tableName = "random_cards")
data class RandomCardEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String?,
    val types: List<String>?
)

fun NetworkCard.asRandomCardEntity(): RandomCardEntity =
    RandomCardEntity(id = id, name = name, imageUrl = imageUrl, types = types)

fun RandomCardEntity.asExternalModel(): CardPreview =
    CardPreview(id = id, name = name, imageUrl = imageUrl)
