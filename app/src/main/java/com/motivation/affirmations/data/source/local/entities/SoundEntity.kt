package com.motivation.affirmations.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.motivation.affirmations.domain.model.Sound

@Entity
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
    val objectId: String
)

fun SoundEntity.asDomainModel(): Sound {
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
        objectId = objectId
    )
}