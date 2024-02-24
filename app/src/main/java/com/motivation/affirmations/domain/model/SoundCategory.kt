package com.motivation.affirmations.domain.model

data class SoundCategory(
    val created: Long,
    val id: Int,
    val objectId: String,
    val ownerId: String? = null,
    val titleEn: String,
    val updated: String? = null
)
