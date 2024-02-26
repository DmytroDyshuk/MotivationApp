package com.motivation.affirmations.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.motivation.affirmations.data.source.local.dao.SoundCategoryDao
import com.motivation.affirmations.data.source.local.dao.SoundDao
import com.motivation.affirmations.data.source.local.dao.SoundFileDao
import com.motivation.affirmations.data.source.local.entities.SoundCategoryEntity
import com.motivation.affirmations.data.source.local.entities.SoundEntity

@Database(entities = [SoundEntity::class, SoundCategoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val soundDao: SoundDao
    abstract val soundCategoryDao: SoundCategoryDao
    abstract val soundFileDao: SoundFileDao
}