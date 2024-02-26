package com.motivation.affirmations.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.motivation.affirmations.domain.model.SoundCategory

@Entity(tableName = "sound_categories")
data class SoundCategoryEntity constructor(
    @PrimaryKey(autoGenerate = false)
    val created: Long,
    val classType: String,
    val titleEn: String,
    val id: Int,
    val objectId: String,
    val ownerId: String?
)

fun SoundCategoryEntity.asDomainModel(): SoundCategory {
    return SoundCategory(
        created = created,
        classType = classType,
        titleEn = titleEn,
        id = id,
        objectId = objectId,
        ownerId = ownerId
    )
}
