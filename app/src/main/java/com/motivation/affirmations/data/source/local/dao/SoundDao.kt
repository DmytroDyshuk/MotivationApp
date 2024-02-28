package com.motivation.affirmations.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.motivation.affirmations.data.source.local.entities.SoundEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSounds(soundsList: List<SoundEntity>)

    @Query("DELETE FROM sounds")
    fun deleteAllSounds()

    @Query("SELECT * FROM sounds WHERE categoryId = :categoryId")
    fun getSoundsByCategoryId(categoryId: Int): List<SoundEntity>

    @Query("SELECT * FROM sounds WHERE isFavorite = 1")
    fun getFavoriteSounds(): Flow<List<SoundEntity>>

    @Query("UPDATE sounds SET isFavorite = :isFavorite WHERE id = :soundId")
    fun updateFavoriteStatus(soundId: Int, isFavorite: Boolean)
}