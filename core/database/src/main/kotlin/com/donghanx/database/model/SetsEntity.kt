package com.donghanx.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.donghanx.model.SetInfo
import com.donghanx.model.network.NetworkSet

@Entity(tableName = "sets")
data class SetEntity(
    @PrimaryKey val scryfallId: String,
    val code: String,
    val cardCount: Int,
    val digital: Boolean,
    val iconSvgUri: String,
    val name: String,
    val setType: String,
    val releasedAt: String,
    val scryfallUri: String,
    val searchUri: String,
    val uri: String
)

fun NetworkSet.asSetEntity(): SetEntity =
    SetEntity(
        scryfallId = scryfallId,
        code = code,
        cardCount = cardCount,
        digital = digital,
        iconSvgUri = iconSvgUri,
        name = name,
        setType = setType,
        releasedAt = releasedAt,
        scryfallUri = scryfallUri,
        searchUri = searchUri,
        uri = uri
    )

fun SetEntity.asExternalModel(): SetInfo =
    SetInfo(
        scryfallId = scryfallId,
        code = code,
        cardCount = cardCount,
        digital = digital,
        iconSvgUri = iconSvgUri,
        name = name,
        setType = setType,
        releasedAt = releasedAt,
        scryfallUri = scryfallUri,
        searchUri = searchUri,
        uri = uri
    )
