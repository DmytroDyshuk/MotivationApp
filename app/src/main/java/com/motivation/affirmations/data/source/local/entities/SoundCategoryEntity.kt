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
        created = this@asDomainModel.created,
        classType = this@asDomainModel.classType,
        titleEn = this@asDomainModel.titleEn,
        id = this@asDomainModel.id,
        objectId = this@asDomainModel.objectId,
        ownerId = this@asDomainModel.ownerId
    )
}

fun List<SoundCategoryEntity>.asListDomainModel(): List<SoundCategory> {
    return map {
        SoundCategory(
            created = it.created,
            classType = it.classType,
            titleEn = it.titleEn,
            id = it.id,
            objectId = it.objectId,
            ownerId = it.ownerId
        )
    }
}
