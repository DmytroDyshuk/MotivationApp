package com.motivation.affirmations.di

import com.motivation.affirmations.data.repository.SoundCategoryRepositoryImpl
import com.motivation.affirmations.data.repository.SoundRepositoryImpl
import com.motivation.affirmations.domain.repository.SoundCategoryRepository
import com.motivation.affirmations.domain.repository.SoundRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 17.02.2022.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSoundCategoryRepository(
        soundCategoryRepositoryImpl: SoundCategoryRepositoryImpl
    ): SoundCategoryRepository

    @Binds
    @Singleton
    abstract fun bindSoundRepository(
        soundRepositoryImpl: SoundRepositoryImpl
    ): SoundRepository

}
