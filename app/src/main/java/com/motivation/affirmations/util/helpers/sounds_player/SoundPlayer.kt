package com.motivation.affirmations.util.helpers.sounds_player

import android.media.MediaPlayer
import com.motivation.affirmations.util.Defaults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundPlayer @Inject constructor(
    private val externalScope: CoroutineScope,
    private val mediaPlayer: MediaPlayer
) {

    private var totalSoundDuration: Int = 0
    private var currentPlayingSoundName = ""
    private var progressJob: Job? = null

    val soundProgressFlow = MutableStateFlow(0)

    fun play(soundName: String, type: SoundPlayType, soundCompletionListener: SoundCompletionListener) {
        val url = when (type) {
            SoundPlayType.PREVIEW -> Defaults.SOUND_PREVIEW_FOLDER_URL + soundName
            SoundPlayType.FULL -> Defaults.SOUND_FILES_FOLDER_URL + soundName
        }

        if (mediaPlayer.isPlaying && currentPlayingSoundName == soundName) {
            mediaPlayer.stop()
        } else {
            mediaPlayer.run {
                reset()
                setDataSource(url)
                setOnCompletionListener {
                    soundCompletionListener.onSoundComplete()
                    resetPlayer()
                }
                setOnPreparedListener {
                    start()
                    totalSoundDuration = it.duration
                    startProgressListener()
                }
                prepareAsync()
            }
            currentPlayingSoundName = soundName
        }
    }

    private fun startProgressListener() {
        progressJob?.cancel()
        progressJob = externalScope.launch {
            while (true) {
                mediaPlayer.let { player ->
                    if (player.isPlaying) {
                        val currentPosition = player.currentPosition
                        val progress = (currentPosition.toDouble() / totalSoundDuration * 100).toInt()
                        soundProgressFlow.emit(progress)
                    }
                }
                delay(100)
            }
        }
    }

    fun resetPlayer() {
        mediaPlayer.reset()
        progressJob?.cancel()
        progressJob = null
    }

}
