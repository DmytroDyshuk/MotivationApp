package com.motivation.affirmations.domain.model

data class SoundCategory(
    val created: Long,
    val classType: String,
    val titleEn: String,
    val id: Int,
    val objectId: String,
    val ownerId: String? = null
)
