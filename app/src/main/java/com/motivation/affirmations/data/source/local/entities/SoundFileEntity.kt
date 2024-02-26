package com.motivation.affirmations.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.motivation.affirmations.domain.model.SoundFile

@Entity(tableName = "sound_files")
data class SoundFileEntity constructor(
    val name: String,
    @PrimaryKey(autoGenerate = false)
    val createdOn: Long,
    val updateOn: Long,
    val publicUrl: String,
    val size: Int,
    val url: String
)

fun SoundFileEntity.asDomainModel(): SoundFile {
    return SoundFile(
        name = this@asDomainModel.name,
        createdOn = this@asDomainModel.createdOn,
        updateOn = this@asDomainModel.updateOn,
        publicUrl = this@asDomainModel.publicUrl,
        size = this@asDomainModel.size,
        url = this@asDomainModel.url
    )
}
