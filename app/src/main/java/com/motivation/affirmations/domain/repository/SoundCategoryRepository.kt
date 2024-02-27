package com.motivation.affirmations.domain.repository

import com.motivation.affirmations.domain.model.SoundCategory
import kotlinx.coroutines.flow.StateFlow

interface SoundCategoryRepository {
    fun getSoundCategoriesFlow(): StateFlow<List<SoundCategory>>
    suspend fun refreshSoundCategories()
}