package com.motivation.affirmations.data.source.remote.api

import com.motivation.affirmations.data.source.remote.models.SoundCategoryRemote
import retrofit2.http.GET

interface SoundCategoryApi {
    @GET("data/SoundCategory")
    suspend fun getAllSoundCategories(): List<SoundCategoryRemote>
}