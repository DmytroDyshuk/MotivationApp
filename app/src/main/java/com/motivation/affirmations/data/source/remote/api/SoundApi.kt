package com.motivation.affirmations.data.source.remote.api

import com.motivation.affirmations.data.source.remote.models.SoundRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface SoundApi {
    @GET("data/Sound")
    suspend fun getSounds(): List<SoundRemote>

    @GET("data/Sound")
    suspend fun getSoundsByCategory(@Query("where") categoryId: Int): List<SoundRemote>
}