package com.motivation.affirmations.di

import android.content.Context
import androidx.room.Room
import com.motivation.affirmations.data.source.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 17.02.2022.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(
        context = appContext,
        klass = AppDatabase::class.java,
        name = "app_database"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideSoundDao(appDatabase: AppDatabase) = appDatabase.soundDao

    @Provides
    @Singleton
    fun provideSoundCategoryDao(appDatabase: AppDatabase) = appDatabase.soundCategoryDao

    @Provides
    @Singleton
    fun provideSoundFileDao(appDatabase: AppDatabase) = appDatabase.soundFileDao
}
