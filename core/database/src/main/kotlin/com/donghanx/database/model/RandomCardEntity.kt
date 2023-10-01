package com.donghanx.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.donghanx.model.Card
import com.donghanx.model.NetworkCard

@Entity(tableName = "random_cards")
data class RandomCardEntity(
    @PrimaryKey val id: String,
    val imageUrl: String?,
    val manaCost: String?,
    val multiverseId: String?,
    val name: String,
    val number: String,
    val power: String?,
    val rarity: String,
    val set: String,
    val setName: String,
    val text: String?,
    val type: String,
)

fun RandomCardEntity.asExternalModel(): Card = Card(id = id, name = name, text = text.orEmpty())

fun NetworkCard.asRandomCardEntity(): RandomCardEntity =
    RandomCardEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        manaCost = manaCost,
        multiverseId = multiverseId,
        number = number,
        power = power,
        rarity = rarity,
        set = set,
        setName = setName,
        text = text,
        type = type
    )
