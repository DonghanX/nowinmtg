package com.donghanx.mock

import com.donghanx.model.CardDetails
import com.donghanx.model.CardPreview
import com.donghanx.model.SetInfo
import com.donghanx.model.network.ImageUris

object MockUtils {
    val emptyCards = List(5) { CardPreview(id = it.toString(), name = "", imageUrl = "") }

    val cardDetailsProgenitus: CardDetails =
        CardDetails(
            id = "bcc764b0-3046-4bde-b424-c0f4e1a6169b",
            multiverseId = 179496,
            name = "Progenitus",
            manaCost = "{W}{W}{U}{U}{B}{B}{R}{R}{G}{G}",
            cmc = 10.0,
            colors = listOf("B", "G", "R", "U", "W"),
            colorIdentity = listOf("B", "G", "R", "U", "W"),
            typeLine = "Legendary Creature — Hydra Avatar",
            rarity = "mythic",
            set = "con",
            setName = "Conflux",
            text =
                "Protection from everything\\nIf Progenitus would be put into a graveyard from anywhere, reveal Progenitus and shuffle it into its owner's library instead.",
            artist = "Jaime Jones",
            power = "10",
            toughness = "10",
            layout = "normal",
            flavor = "The Soul of the World has returned.",
            imageUris =
                ImageUris(
                    artCrop = "",
                    borderCrop = "",
                    large = "",
                    normal = "",
                    png = "",
                    small = "",
                ),
        )

    val cardDetailsIncomplete: CardDetails =
        CardDetails(
            id = "bcc764b0-3046-4bde-b424-c0f4e1a6169b",
            multiverseId = 409741,
            name = "",
            manaCost = null,
            cmc = 5.0,
            colors = null,
            colorIdentity = null,
            typeLine = "Legendary Creature — Angel",
            rarity = "Mythic Rare",
            set = "SOI",
            text = null,
            artist = null,
            power = null,
            toughness = null,
            layout = "double-faced",
            imageUris = null,
            flavor = null,
            setName = "Shadows over Innistrad",
        )

    val soiExpansion =
        SetInfo(
            scryfallId = "5e914d7e-c1e9-446c-a33d-d093c02b2743",
            code = "soi",
            name = "Shadows over Innistrad",
            cardCount = 297,
            digital = false,
            iconSvgUri = "https://svgs.scryfall.io/sets/soi.svg?1699246800",
            setType = "expansion",
            releasedAt = "2016-04-08",
            scryfallUri = "https://scryfall.com/sets/soi",
            searchUri =
                "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Asoi&unique=prints",
            uri = "https://api.scryfall.com/sets/5e914d7e-c1e9-446c-a33d-d093c02b2743",
        )

    val xlnExpansion =
        SetInfo(
            scryfallId = "fe0dad85-54bc-4151-9200-d68da84dd0f2",
            code = "xln",
            name = "Ixalan",
            cardCount = 289,
            digital = false,
            iconSvgUri = "https://svgs.scryfall.io/sets/xln.svg?1699246800",
            setType = "expansion",
            releasedAt = "2017-09-29",
            scryfallUri = "https://scryfall.com/sets/xln",
            searchUri =
                "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Axln&unique=prints",
            uri = "https://api.scryfall.com/sets/fe0dad85-54bc-4151-9200-d68da84dd0f2",
        )
}
