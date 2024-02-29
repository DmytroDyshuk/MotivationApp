package com.motivation.affirmations.data.repository

import com.motivation.affirmations.data.source.local.dao.SoundDao
import com.motivation.affirmations.data.source.local.entities.asListDomainModel
import com.motivation.affirmations.data.source.remote.api.SoundApi
import com.motivation.affirmations.data.source.remote.models.asEntity
import com.motivation.affirmations.di.IoDispatcher
import com.motivation.affirmations.domain.model.Sound
import com.motivation.affirmations.domain.model.asEntity
import com.motivation.affirmations.domain.repository.SoundRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundRepositoryImpl @Inject constructor(
    private val soundApi: SoundApi,
    private val soundDao: SoundDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val externalScope: CoroutineScope
): SoundRepository {

    override suspend fun getSoundsListByCategory(categoryId: Int) = withContext(ioDispatcher) {
        return@withContext soundDao.getSoundsByCategoryId(categoryId).asListDomainModel()
    }

    override suspend fun updateSoundsDb() {
        withContext(ioDispatcher) {
            val currentFavouriteSounds = soundDao.getFavoriteSounds().first()
            val soundsResponse = soundApi.getSounds()
            soundDao.deleteAllSounds()
            soundDao.insertAllSounds(soundsResponse.map { it.asEntity() })
            currentFavouriteSounds.forEach { favoriteSound ->
                soundDao.updateFavoriteStatus(favoriteSound.id, true)
            }
        }
    }

    override suspend fun addSoundsToFavourites(sounds: List<Sound>) {
        withContext(ioDispatcher) {
            soundDao.insertAllSounds(sounds.map { it.copy().asEntity() })
        }
    }

    override fun getFavouriteSoundsFlow(): StateFlow<List<Sound>> {
        return soundDao.getFavoriteSounds().map {
            it.asListDomainModel()
        }.stateIn(
            scope = externalScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )
    }

}