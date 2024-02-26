package com.motivation.affirmations.data.source.remote.models

import com.motivation.affirmations.data.source.local.entities.SoundFileEntity
import com.motivation.affirmations.domain.model.SoundFile
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SoundFileRemote(
    val name: String,
    val createdOn: Long,
    val updateOn: Long,
    val publicUrl: String,
    val size: Int,
    val url: String
)

fun SoundFileRemote.asEntity(): SoundFileEntity {
    return SoundFileEntity(
        name = this@asEntity.name,
        createdOn = this@asEntity.createdOn,
        updateOn = this@asEntity.updateOn,
        publicUrl = this@asEntity.publicUrl,
        size = this@asEntity.size,
        url = this@asEntity.url
    )
}

fun SoundFileRemote.asDomainModel(): SoundFile {
    return SoundFile(
        name = this@asDomainModel.name,
        createdOn = this@asDomainModel.createdOn,
        updateOn = this@asDomainModel.updateOn,
        publicUrl = this@asDomainModel.publicUrl,
        size = this@asDomainModel.size,
        url = this@asDomainModel.url
    )
}