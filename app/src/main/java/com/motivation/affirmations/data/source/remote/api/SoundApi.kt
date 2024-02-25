package com.motivation.affirmations.data.source.remote.api

import com.motivation.affirmations.data.source.remote.models.SoundRemote
import retrofit2.http.GET

interface SoundApi {
    @GET("data/Sound")
    suspend fun getSound(): SoundRemote
}