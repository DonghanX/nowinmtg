package com.donghanx.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.donghanx.model.CardPreview
import com.donghanx.model.network.NetworkCard

@Entity(tableName = "random_cards")
data class RandomCardEntity(
    @PrimaryKey val multiverseId: Int,
    val name: String,
    val imageUrl: String?,
    val types: List<String>?,
)

fun NetworkCard.asRandomCardEntity(): RandomCardEntity =
    RandomCardEntity(
        multiverseId = multiverseId,
        name = name,
        imageUrl = imageUrl,
        types = types,
    )

fun RandomCardEntity.asExternalModel(): CardPreview =
    CardPreview(id = multiverseId, name = name, imageUrl = imageUrl)
