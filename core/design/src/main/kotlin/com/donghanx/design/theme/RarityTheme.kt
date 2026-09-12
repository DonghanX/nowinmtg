package com.donghanx.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.donghanx.model.Rarity

data class RarityColorScheme(
    val mythic: ColorFamily,
    val rare: ColorFamily,
    val uncommon: ColorFamily,
    val common: ColorFamily,
    val unknown: ColorFamily,
)

private val commonLightFamily =
    ColorFamily(
        rarityCommonLight,
        onRarityCommonLight,
        rarityCommonContainerLight,
        onRarityCommonContainerLight,
    )

private val commonDarkFamily =
    ColorFamily(
        rarityCommonDark,
        onRarityCommonDark,
        rarityCommonContainerDark,
        onRarityCommonContainerDark,
    )

internal val lightRarityColorScheme =
    RarityColorScheme(
        common = commonLightFamily,
        uncommon =
            ColorFamily(
                rarityUncommonLight,
                onRarityUncommonLight,
                rarityUncommonContainerLight,
                onRarityUncommonContainerLight,
            ),
        rare =
            ColorFamily(
                rarityRareLight,
                onRarityRareLight,
                rarityRareContainerLight,
                onRarityRareContainerLight,
            ),
        mythic =
            ColorFamily(
                rarityMythicLight,
                onRarityMythicLight,
                rarityMythicContainerLight,
                onRarityMythicContainerLight,
            ),
        unknown = commonLightFamily,
    )

internal val darkRarityColorScheme =
    RarityColorScheme(
        common = commonDarkFamily,
        uncommon =
            ColorFamily(
                rarityUncommonDark,
                onRarityUncommonDark,
                rarityUncommonContainerDark,
                onRarityUncommonContainerDark,
            ),
        rare =
            ColorFamily(
                rarityRareDark,
                onRarityRareDark,
                rarityRareContainerDark,
                onRarityRareContainerDark,
            ),
        mythic =
            ColorFamily(
                rarityMythicDark,
                onRarityMythicDark,
                rarityMythicContainerDark,
                onRarityMythicContainerDark,
            ),
        unknown = commonDarkFamily,
    )

fun RarityColorScheme.fromRarity(rarity: Rarity): ColorFamily =
    when (rarity) {
        Rarity.Mythic -> mythic
        Rarity.Rare -> rare
        Rarity.Uncommon -> uncommon
        Rarity.Common -> common
        else -> unknown
    }

val LocalRarityColorScheme = staticCompositionLocalOf { lightRarityColorScheme }

object RarityTheme {
    val colors: RarityColorScheme
        @Composable @ReadOnlyComposable get() = LocalRarityColorScheme.current
}
