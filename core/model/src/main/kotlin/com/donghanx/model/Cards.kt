package com.donghanx.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable data class Cards(@SerialName("cards") val cards: List<Card>)

@Serializable
data class Card(
    @SerialName("artist") val artist: String,
    @SerialName("cmc") val cmc: Double,
    @SerialName("colorIdentity") val colorIdentity: List<String>?,
    @SerialName("colors") val colors: List<String>?,
    @SerialName("flavor") val flavor: String?,
    @SerialName("foreignNames") val foreignNames: List<ForeignName>?,
    @SerialName("id") val id: String,
    @SerialName("imageUrl") val imageUrl: String?,
    @SerialName("layout") val layout: String,
    @SerialName("legalities") val legalities: List<Legality>?,
    @SerialName("manaCost") val manaCost: String?,
    @SerialName("multiverseid") val multiverseId: String?,
    @SerialName("name") val name: String,
    @SerialName("number") val number: String,
    @SerialName("originalText") val originalText: String?,
    @SerialName("originalType") val originalType: String?,
    @SerialName("power") val power: String?,
    @SerialName("printings") val printings: List<String>,
    @SerialName("rarity") val rarity: String,
    @SerialName("rulings") val rulings: List<Ruling>?,
    @SerialName("set") val set: String,
    @SerialName("setName") val setName: String,
    @SerialName("subtypes") val subtypes: List<String>?,
    @SerialName("supertypes") val supertypes: List<String>?,
    @SerialName("text") val text: String?,
    @SerialName("toughness") val toughness: String?,
    @SerialName("type") val type: String,
    @SerialName("types") val types: List<String>,
    @SerialName("variations") val variations: List<String>?
)

@Serializable
data class ForeignName(
    @SerialName("flavor") val flavor: String?,
    @SerialName("imageUrl") val imageUrl: String?,
    @SerialName("language") val language: String,
    @SerialName("multiverseid") val multiverseId: String?,
    @SerialName("name") val name: String,
    @SerialName("faceName") val faceName: String?,
    @SerialName("text") val text: String?,
    @SerialName("type") val type: String?
)

@Serializable
data class Legality(
    @SerialName("format") val format: String,
    @SerialName("legality") val legality: String
)

@Serializable
data class Ruling(@SerialName("date") val date: String, @SerialName("text") val text: String)
