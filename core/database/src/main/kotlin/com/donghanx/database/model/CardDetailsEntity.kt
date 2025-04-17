package com.donghanx.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.donghanx.model.CardDetails
import com.donghanx.model.CardPreview
import com.donghanx.model.network.ImageUris
import com.donghanx.model.network.NetworkCardDetails

@Entity(tableName = "card_details")
data class CardDetailsEntity(
    @PrimaryKey val id: String,
    val multiverseId: Int?,
    val artist: String?,
    val cmc: Double?,
    val colorIdentity: List<String>?,
    val colors: List<String>?,
    val flavor: String?,
    @Embedded val imageUris: ImageUris?,
    val layout: String,
    val manaCost: String?,
    val name: String,
    val text: String?,
    val typeLine: String?,
    val power: String?,
    val rarity: String,
    val set: String,
    val setName: String,
    val toughness: String?,
)

fun NetworkCardDetails.asCardDetailsEntity(): CardDetailsEntity =
    CardDetailsEntity(
        id = id,
        multiverseId = multiverseIds?.firstOrNull(),
        artist = artist,
        cmc = cmc,
        colorIdentity = colorIdentity,
        colors = colors,
        flavor = flavorText,
        imageUris = imageUris,
        layout = layout,
        manaCost = manaCost,
        name = name,
        text = oracleText,
        typeLine = typeLine,
        power = power,
        rarity = rarity,
        set = set,
        setName = setName,
        toughness = toughness,
    )

fun CardDetailsEntity.asExternalModel(): CardDetails =
    CardDetails(
        id = id,
        multiverseId = multiverseId,
        artist = artist,
        cmc = cmc,
        colorIdentity = colorIdentity,
        colors = colors,
        flavor = flavor,
        imageUris = imageUris,
        layout = layout,
        manaCost = manaCost,
        name = name,
        text = text,
        typeLine = typeLine,
        power = power,
        rarity = rarity,
        set = set,
        setName = setName,
        toughness = toughness,
    )

fun CardDetailsEntity.asExternalPreviewModel(): CardPreview =
    CardPreview(id = id, name = name, imageUrl = imageUris?.png)
