package com.motivation.affirmations.data.source.remote.models

import com.motivation.affirmations.domain.model.SoundCategory
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class SoundCategoryRemote(
    val created: Long,
    @Json(name = "___class")
    val classType: String,
    @Json(name = "title_en")
    val titleEn: String,
    val id: Int,
    val ownerId: String?,
    val updated: Date?,
    val objectId: String
)

fun SoundCategoryRemote.asDomainModel(): SoundCategory {
    return SoundCategory(
        created = created,
        classType = classType,
        titleEn = titleEn,
        id = id,
        objectId = objectId,
        ownerId = ownerId,
        updated = updated
    )
}
