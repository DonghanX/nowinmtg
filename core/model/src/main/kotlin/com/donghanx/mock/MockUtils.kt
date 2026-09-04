package com.donghanx.mock

import com.donghanx.model.CardDetails
import com.donghanx.model.CardPreview
import com.donghanx.model.Ruling
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

    val rulingsProgenitus =
        listOf(
            Ruling(
                comment =
                    "Protection from everything” means the following: Progenitus can’t be blocked, Progenitus can’t be enchanted or equipped, Progenitus can’t be the target of spells or abilities, and all damage that would be dealt to Progenitus is prevented.",
                publishedAt = "2009-02-01",
                source = "wotc",
            ),
            Ruling(
                comment =
                    "Progenitus can still be affected by effects that don’t target it or deal damage to it (such as Day of Judgment).",
                publishedAt = "2009-02-01",
                source = "wotc",
            ),
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

    val zendikarRisingSets =
        listOf(
            SetInfo(
                scryfallId = "f4e01fa7-b254-42dd-849f-69b58027a8c4",
                code = "znr",
                name = "Zendikar Rising",
                cardCount = 407,
                digital = false,
                iconSvgUri = "https://svgs.scryfall.io/sets/znr.svg?1787544000",
                setType = "expansion",
                releasedAt = "2020-09-25",
                scryfallUri = "https://scryfall.com/sets/znr",
                searchUri =
                    "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Aznr&unique=prints",
                uri = "https://api.scryfall.com/sets/f4e01fa7-b254-42dd-849f-69b58027a8c4",
            ),
            SetInfo(
                scryfallId = "25c6bd9b-4e10-40a4-b9b5-0f4d9b5852a1",
                code = "znc",
                name = "Zendikar Rising Commander",
                cardCount = 142,
                digital = false,
                iconSvgUri = "https://svgs.scryfall.io/sets/znc.svg?1787544000",
                setType = "commander",
                releasedAt = "2020-09-25",
                scryfallUri = "https://scryfall.com/sets/znc",
                searchUri =
                    "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Aznc&unique=prints",
                uri = "https://api.scryfall.com/sets/25c6bd9b-4e10-40a4-b9b5-0f4d9b5852a1",
            ),
            SetInfo(
                scryfallId = "76c61d42-610a-4b5a-880a-7a1fc1222f81",
                code = "pznr",
                name = "Zendikar Rising Promos",
                cardCount = 153,
                digital = false,
                iconSvgUri = "https://svgs.scryfall.io/sets/znr.svg?1787544000",
                setType = "promo",
                releasedAt = "2020-09-25",
                scryfallUri = "https://scryfall.com/sets/pznr",
                searchUri =
                    "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Apznr&unique=prints",
                uri = "https://api.scryfall.com/sets/76c61d42-610a-4b5a-880a-7a1fc1222f81",
            ),
            SetInfo(
                scryfallId = "168acc08-0dea-40e7-ab0d-93bb2832e72b",
                code = "aznr",
                name = "Zendikar Rising Art Series",
                cardCount = 81,
                digital = false,
                iconSvgUri = "https://svgs.scryfall.io/sets/znr.svg?1787544000",
                setType = "memorabilia",
                releasedAt = "2020-09-25",
                scryfallUri = "https://scryfall.com/sets/aznr",
                searchUri =
                    "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Aaznr&unique=prints",
                uri = "https://api.scryfall.com/sets/168acc08-0dea-40e7-ab0d-93bb2832e72b",
            ),
            SetInfo(
                scryfallId = "24e668b5-8312-498e-9bd7-c77a102bb55c",
                code = "zne",
                name = "Zendikar Rising Expeditions",
                cardCount = 30,
                digital = false,
                iconSvgUri = "https://svgs.scryfall.io/sets/zne.svg?1787544000",
                setType = "masterpiece",
                releasedAt = "2020-09-25",
                scryfallUri = "https://scryfall.com/sets/zne",
                searchUri =
                    "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Azne&unique=prints",
                uri = "https://api.scryfall.com/sets/24e668b5-8312-498e-9bd7-c77a102bb55c",
            ),
            SetInfo(
                scryfallId = "ee023dc4-fe71-4224-be95-7c889d771ee1",
                code = "tznr",
                name = "Zendikar Rising Tokens",
                cardCount = 12,
                digital = false,
                iconSvgUri = "https://svgs.scryfall.io/sets/znr.svg?1787544000",
                setType = "token",
                releasedAt = "2020-09-25",
                scryfallUri = "https://scryfall.com/sets/tznr",
                searchUri =
                    "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Atznr&unique=prints",
                uri = "https://api.scryfall.com/sets/ee023dc4-fe71-4224-be95-7c889d771ee1",
            ),
            SetInfo(
                scryfallId = "cfb7e832-fe79-4b5c-ba3d-2d59009525e7",
                code = "tznc",
                name = "Zendikar Rising Commander Tokens",
                cardCount = 11,
                digital = false,
                iconSvgUri = "https://svgs.scryfall.io/sets/znc.svg?1787544000",
                setType = "token",
                releasedAt = "2020-09-25",
                scryfallUri = "https://scryfall.com/sets/tznc",
                searchUri =
                    "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Atznc&unique=prints",
                uri = "https://api.scryfall.com/sets/cfb7e832-fe79-4b5c-ba3d-2d59009525e7",
            ),
            SetInfo(
                scryfallId = "3bcb5e53-5ebb-4ff4-b76a-fc378bca0157",
                code = "sznr",
                name = "Zendikar Rising Substitute Cards",
                cardCount = 9,
                digital = false,
                iconSvgUri = "https://svgs.scryfall.io/sets/znr.svg?1787544000",
                setType = "token",
                releasedAt = "2020-09-25",
                scryfallUri = "https://scryfall.com/sets/sznr",
                searchUri =
                    "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Asznr&unique=prints",
                uri = "https://api.scryfall.com/sets/3bcb5e53-5ebb-4ff4-b76a-fc378bca0157",
            ),
            SetInfo(
                scryfallId = "40f22d42-6fa9-4de4-8423-916a1b2268ab",
                code = "mznr",
                name = "Zendikar Rising Minigames",
                cardCount = 5,
                digital = false,
                iconSvgUri = "https://svgs.scryfall.io/sets/znr.svg?1787544000",
                setType = "minigame",
                releasedAt = "2020-09-25",
                scryfallUri = "https://scryfall.com/sets/mznr",
                searchUri =
                    "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Amznr&unique=prints",
                uri = "https://api.scryfall.com/sets/40f22d42-6fa9-4de4-8423-916a1b2268ab",
            ),
        )

    val newPhyrexiaSets =
        listOf(
            SetInfo(
                scryfallId = "e8e356d8-6d01-4dab-aa07-d0999dc9359f",
                code = "nph",
                name = "New Phyrexia",
                cardCount = 175,
                digital = false,
                iconSvgUri = "https://svgs.scryfall.io/sets/nph.svg?1787544000",
                setType = "expansion",
                releasedAt = "2011-05-13",
                scryfallUri = "https://scryfall.com/sets/nph",
                searchUri =
                    "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Anph&unique=prints",
                uri = "https://api.scryfall.com/sets/e8e356d8-6d01-4dab-aa07-d0999dc9359f",
            ),
            SetInfo(
                scryfallId = "7b5e3883-6588-412a-b979-7de44d50b3de",
                code = "tnph",
                name = "New Phyrexia Tokens",
                cardCount = 5,
                digital = false,
                iconSvgUri = "https://svgs.scryfall.io/sets/nph.svg?1787544000",
                setType = "token",
                releasedAt = "2011-05-13",
                scryfallUri = "https://scryfall.com/sets/tnph",
                searchUri =
                    "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Atnph&unique=prints",
                uri = "https://api.scryfall.com/sets/7b5e3883-6588-412a-b979-7de44d50b3de",
            ),
            SetInfo(
                scryfallId = "659f3361-e6e9-4891-925f-1d6795bab6ab",
                code = "pnph",
                name = "New Phyrexia Promos",
                cardCount = 4,
                digital = false,
                iconSvgUri = "https://svgs.scryfall.io/sets/nph.svg?1787544000",
                setType = "promo",
                releasedAt = "2011-05-12",
                scryfallUri = "https://scryfall.com/sets/pnph",
                searchUri =
                    "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Apnph&unique=prints",
                uri = "https://api.scryfall.com/sets/659f3361-e6e9-4891-925f-1d6795bab6ab",
            ),
        )

    val setsAcrossReleaseYears: List<SetInfo> = zendikarRisingSets + newPhyrexiaSets
}
