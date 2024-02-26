package com.motivation.affirmations.data.source.remote.api

import com.motivation.affirmations.data.source.remote.models.SoundFileRemote
import retrofit2.http.GET

interface SoundFileApi {
    @GET("files/sounds")
    fun getAllSoundFiles(): List<SoundFileRemote>

    @GET("files/sounds_preview")
    fun getAllSoundPreviewFiles(): List<SoundFileRemote>
}