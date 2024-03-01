package com.motivation.affirmations.domain.repository

import com.motivation.affirmations.domain.model.SoundCategory

interface SoundCategoryRepository {
    suspend fun getSoundCategories(): List<SoundCategory>
    suspend fun refreshSoundCategories()
}