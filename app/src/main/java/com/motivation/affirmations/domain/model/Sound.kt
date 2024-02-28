package com.motivation.affirmations.domain.model

import com.motivation.affirmations.data.source.local.entities.SoundEntity


data class Sound(
    val created: Long,
    val previewName: String,
    val soundName: String,
    val ownerId: Int? = null,
    val categoryId: Int,
    val thumbnailName: String,
    val titleEn: String,
    val id: Int,
    val locked: Boolean,
    val objectId: String,
    val isFavorite: Boolean = false
)

fun Sound.asEntity(): SoundEntity {
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
        objectId = this@asEntity.objectId,
        isFavorite = this@asEntity.isFavorite
    )
}

fun List<Sound>.asListEntity(): List<SoundEntity> {
    return map {
        SoundEntity(
            created = it.created,
            previewName = it.previewName,
            soundName = it.soundName,
            ownerId = it.ownerId,
            categoryId = it.categoryId,
            thumbnailName = it.thumbnailName,
            titleEn = it.titleEn,
            id = it.id,
            locked = it.locked,
            objectId = it.objectId,
            isFavorite = it.isFavorite
        )
    }
}