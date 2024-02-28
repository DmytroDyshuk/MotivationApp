package com.motivation.affirmations.util.helpers.sounds_player

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.motivation.affirmations.util.Defaults

object SoundPlayer {
    private var onStartListener: (() -> Unit)? = null
    private var onCompletionListener: (() -> Unit)? = null

    private var mediaPlayer: MediaPlayer? = null

    enum class SoundPlayType {
        PREVIEW,
        FULL
    }

    fun play(soundName: String, type: SoundPlayType) {
        stop()

        val url = when (type) {
            SoundPlayType.PREVIEW -> Defaults.SOUND_PREVIEW_FOLDER_URL + soundName
            SoundPlayType.FULL -> Defaults.SOUND_FILES_FOLDER_URL + soundName
        }

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )

            try {
                setDataSource(url)
                prepareAsync()
                setOnPreparedListener { mp ->
                    mp.start()
                    onStartListener?.invoke()
                }
                setOnCompletionListener {
                    onCompletionListener?.invoke()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun stop() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
            mediaPlayer = null
        }
    }

    fun setOnStartListener(listener: () -> Unit) {
        onStartListener = listener
    }

    fun setOnCompletionListener(listener: () -> Unit) {
        onCompletionListener = listener
    }

}