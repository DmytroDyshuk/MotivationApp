package com.motivation.affirmations.domain.model

import java.util.Date

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
    val updated: Date? = null,
    val objectId: String
)