package com.motivation.affirmations.data.source.remote.models

import com.motivation.affirmations.domain.model.Sound
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class SoundRemote(
    val created: Long,
    @Json(name = "preview_name")
    val previewName: String,
    @Json(name = "sound_name")
    val soundName: String,
    val ownerId: Int?,
    @Json(name = "category_id")
    val categoryId: Int,
    @Json(name = "thumbnail_name")
    val thumbnailName: String,
    @Json(name = "___class")
    val classType: String,
    @Json(name = "title_en")
    val titleEn: String,
    val id: Int,
    val locked: Boolean,
    val updated: Date?,
    val objectId: String
)

fun SoundRemote.asDomainModel(): Sound {
    return Sound(
        created = created,
        previewName = previewName,
        soundName = soundName,
        ownerId = ownerId,
        categoryId = categoryId,
        thumbnailName = thumbnailName,
        titleEn = titleEn,
        id = id,
        locked = locked,
        updated = updated,
        objectId = objectId
    )
}
