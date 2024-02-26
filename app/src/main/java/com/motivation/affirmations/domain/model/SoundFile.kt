package com.motivation.affirmations.domain.model

data class SoundFile(
    val name: String,
    val createdOn: Long,
    val updateOn: Long,
    val publicUrl: String,
    val size: Int,
    val url: String
)