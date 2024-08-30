package com.donghanx.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.donghanx.model.CardDetails
import com.donghanx.model.CardPreview

@Entity(tableName = "favorite_card")
data class FavoriteCardEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String?,
    val set: String,
    val setName: String,
)

fun CardDetails.asFavoriteCardEntity(): FavoriteCardEntity =
    FavoriteCardEntity(id = id, name = name, imageUrl = imageUrl, set = set, setName = setName)

fun FavoriteCardEntity.asExternalModel(): CardPreview =
    CardPreview(id = id, name = name, imageUrl = imageUrl)
