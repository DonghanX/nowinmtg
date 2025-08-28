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
    val orderIndex: Int,
)

fun NetworkCard.asRandomCardEntity(index: Int): RandomCardEntity =
    RandomCardEntity(
        multiverseId = multiverseId,
        name = name,
        imageUrl = imageUrl,
        types = types,
        // Preserve the order of items in the random cards list when inserting into database
        orderIndex = index,
    )

fun RandomCardEntity.asExternalModel(): CardPreview =
    CardPreview(id = multiverseId.toString(), name = name, imageUrl = imageUrl)
