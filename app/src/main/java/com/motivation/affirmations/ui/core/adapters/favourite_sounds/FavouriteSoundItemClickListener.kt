package com.motivation.affirmations.ui.core.adapters.favourite_sounds

import com.motivation.affirmations.domain.model.Sound

interface FavouriteSoundItemClickListener {
    fun onSoundClicked(sound: Sound, position: Int)
    fun onAddSoundClicked()
}