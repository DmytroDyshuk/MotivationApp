package com.motivation.affirmations.data.repository

import com.motivation.affirmations.data.source.local.dao.SoundCategoryDao
import com.motivation.affirmations.data.source.local.entities.asListDomainModel
import com.motivation.affirmations.data.source.remote.api.SoundCategoryApi
import com.motivation.affirmations.data.source.remote.models.asEntity
import com.motivation.affirmations.di.IoDispatcher
import com.motivation.affirmations.domain.model.SoundCategory
import com.motivation.affirmations.domain.repository.SoundCategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundCategoryRepositoryImpl@Inject constructor(
    private val soundCategoryApi: SoundCategoryApi,
    private val soundCategoryDao: SoundCategoryDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val externalScope: CoroutineScope
) : SoundCategoryRepository {

    override fun getSoundCategoriesFlow(): StateFlow<List<SoundCategory>> {
       return soundCategoryDao.getAllSoundCategories().map {
            it.asListDomainModel()
       }.stateIn(
           scope = externalScope,
           started = SharingStarted.WhileSubscribed(5000),
           initialValue = listOf()
       )
    }

    override suspend fun refreshSoundCategories() {
        withContext(ioDispatcher) {
            val soundCategoriesResponse = soundCategoryApi.getAllSoundCategories()
            soundCategoryDao.deleteAllSoundCategories()
            soundCategoryDao.insertAllSoundCategories(soundCategoriesResponse.map { it.asEntity() })
        }
    }

}