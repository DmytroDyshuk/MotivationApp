package com.motivation.affirmations.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.motivation.affirmations.data.source.local.entities.SoundCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SoundCategoryDao {
    @Query("SELECT * FROM sound_categories")
    fun getAllSoundCategories(): Flow<List<SoundCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSoundCategories(soundCategories: List<SoundCategoryEntity>)

    @Query("DELETE FROM sound_categories")
    fun deleteAllSoundCategories()
}