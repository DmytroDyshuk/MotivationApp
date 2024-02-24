package com.motivation.affirmations.domain.model

data class Sound(
    val created: Long,
    val previewName: String,
    val soundName: String,
    val ownerId: Int? = null,
    val categoryId: Int? = null,
    val thumbnailName: String? = null,
    val titleEn: String? = null,
    val id: Int,
    val locked: Boolean,
    val updated: String? = null,
    val objectId: String
)