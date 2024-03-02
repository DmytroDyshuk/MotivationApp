package com.motivation.affirmations.domain.repository

import com.motivation.affirmations.domain.model.Sound
import kotlinx.coroutines.flow.StateFlow

interface SoundRepository {
    fun getFavouriteSoundsFlow(): StateFlow<List<Sound>>
    suspend fun getSoundsListByCategory(categoryId: Int): List<Sound>
    suspend fun updateSoundsDb()
    suspend fun addSoundsToFavourites(sounds: List<Sound>)
}