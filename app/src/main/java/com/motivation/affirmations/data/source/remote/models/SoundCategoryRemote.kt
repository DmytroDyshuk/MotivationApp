package com.motivation.affirmations.data.source.remote.models

import com.motivation.affirmations.data.source.local.entities.SoundCategoryEntity
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
    val objectId: String
)

fun SoundCategoryRemote.asDomainModel(): SoundCategory {
    return SoundCategory(
        created = this@asDomainModel.created,
        classType = this@asDomainModel.classType,
        titleEn = this@asDomainModel.titleEn,
        id = this@asDomainModel.id,
        objectId = this@asDomainModel.objectId,
        ownerId = this@asDomainModel.ownerId
    )
}

fun SoundCategoryRemote.asEntity(): SoundCategoryEntity {
    return SoundCategoryEntity(
        created = this@asEntity.created,
        classType = this@asEntity.classType,
        titleEn = this@asEntity.titleEn,
        id = this@asEntity.id,
        objectId = this@asEntity.objectId,
        ownerId = this@asEntity.ownerId
    )
}
