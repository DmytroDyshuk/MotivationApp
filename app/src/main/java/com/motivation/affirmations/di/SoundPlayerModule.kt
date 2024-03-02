package com.motivation.affirmations.di

import android.media.MediaPlayer
import com.motivation.affirmations.util.helpers.sounds_player.SoundPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SoundPlayerModule {

    @Singleton
    @Provides
    fun provideMediaPlayer(): MediaPlayer = MediaPlayer()

    @Singleton
    @Provides
    fun provideSoundPlayer(
        externalScope: CoroutineScope,
        mediaPlayer: MediaPlayer
    ): SoundPlayer {
        return SoundPlayer(externalScope, mediaPlayer)
    }
}