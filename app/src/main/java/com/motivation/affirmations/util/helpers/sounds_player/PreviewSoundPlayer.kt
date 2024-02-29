package com.motivation.affirmations.util.helpers.sounds_player

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.motivation.affirmations.util.Defaults

object PreviewSoundPlayer {
    private var onCompletionListener: (() -> Unit)? = null

    private var mediaPlayer: MediaPlayer? = null

    fun play(soundName: String) {
        stop()

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )

            try {
                setDataSource(Defaults.SOUND_PREVIEW_FOLDER_URL + soundName)
                prepareAsync()
                setOnPreparedListener { mp ->
                    mp.start()
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

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    fun setOnCompletionListener(listener: () -> Unit) {
        onCompletionListener = listener
    }

}