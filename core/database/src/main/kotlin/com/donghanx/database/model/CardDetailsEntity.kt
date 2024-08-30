package com.donghanx.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.donghanx.model.CardDetails
import com.donghanx.model.network.NetworkCardDetails
import com.donghanx.model.network.Ruling

@Entity(tableName = "card_details")
data class CardDetailsEntity(
    @PrimaryKey val multiverseId: Int,
    val artist: String?,
    val cmc: Double,
    val colorIdentity: List<String>?,
    val colors: List<String>?,
    val flavor: String?,
    val imageUrl: String?,
    val layout: String,
    val manaCost: String?,
    val name: String,
    val number: String,
    val originalText: String?,
    val originalType: String?,
    val power: String?,
    val printings: List<String>,
    val rarity: String,
    val set: String,
    val setName: String,
    val subtypes: List<String>?,
    val supertypes: List<String>?,
    val text: String?,
    val toughness: String?,
    val type: String,
    val types: List<String>?,
    val variations: List<String>?,
    val rulings: List<Ruling>?,
)

fun NetworkCardDetails.asCardDetailsEntity(): CardDetailsEntity =
    CardDetailsEntity(
        multiverseId = multiverseId,
        artist = artist,
        cmc = cmc,
        colorIdentity = colorIdentity,
        colors = colors,
        flavor = flavor,
        imageUrl = imageUrl,
        layout = layout,
        manaCost = manaCost,
        name = name,
        number = number,
        originalText = originalText,
        originalType = originalType,
        power = power,
        printings = printings,
        rarity = rarity,
        set = set,
        setName = setName,
        subtypes = subtypes,
        supertypes = supertypes,
        text = text,
        toughness = toughness,
        type = type,
        types = types,
        variations = variations,
        rulings = rulings,
    )

fun CardDetailsEntity.asExternalModel(): CardDetails =
    CardDetails(
        multiverseId = multiverseId,
        artist = artist,
        cmc = cmc,
        colorIdentity = colorIdentity,
        colors = colors,
        flavor = flavor,
        imageUrl = imageUrl,
        layout = layout,
        manaCost = manaCost,
        name = name,
        number = number,
        originalText = originalText,
        originalType = originalType,
        power = power,
        printings = printings,
        rarity = rarity,
        set = set,
        setName = setName,
        subtypes = subtypes,
        supertypes = supertypes,
        text = text,
        toughness = toughness,
        type = type,
        types = types,
        variations = variations,
        rulings = rulings,
    )
