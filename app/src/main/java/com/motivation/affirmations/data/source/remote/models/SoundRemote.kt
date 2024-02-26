package com.motivation.affirmations.data.source.remote.models

import com.motivation.affirmations.data.source.local.entities.SoundEntity
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
    val objectId: String
)

fun SoundRemote.asDomainModel(): Sound {
    return Sound(
        created = this@asDomainModel.created,
        previewName = this@asDomainModel.previewName,
        soundName = this@asDomainModel.soundName,
        ownerId = this@asDomainModel.ownerId,
        categoryId = this@asDomainModel.categoryId,
        thumbnailName = this@asDomainModel.thumbnailName,
        titleEn = this@asDomainModel.titleEn,
        id = this@asDomainModel.id,
        locked = this@asDomainModel.locked,
        objectId = this@asDomainModel.objectId
    )
}

fun SoundRemote.asEntity(): SoundEntity {
    return SoundEntity(
        created = this@asEntity.created,
        previewName = this@asEntity.previewName,
        soundName = this@asEntity.soundName,
        ownerId = this@asEntity.ownerId,
        categoryId = this@asEntity.categoryId,
        thumbnailName = this@asEntity.thumbnailName,
        titleEn = this@asEntity.titleEn,
        id = this@asEntity.id,
        locked = this@asEntity.locked,
        objectId = this@asEntity.objectId
    )
}
