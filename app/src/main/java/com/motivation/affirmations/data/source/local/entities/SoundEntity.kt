package com.motivation.affirmations.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.motivation.affirmations.domain.model.Sound

@Entity(tableName = "sounds")
data class SoundEntity constructor(
    @PrimaryKey(autoGenerate = false)
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

fun SoundEntity.asDomainModel(): Sound {
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
        objectId = this@asDomainModel.objectId,
        isFavorite = this@asDomainModel.isFavorite
    )
}

fun List<SoundEntity>.asListDomainModel(): List<Sound> {
    return map {
        Sound(
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