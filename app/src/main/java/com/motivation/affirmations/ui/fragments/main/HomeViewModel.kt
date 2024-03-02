package com.motivation.affirmations.ui.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.motivation.affirmations.domain.repository.SoundRepository
import com.motivation.affirmations.ui.core.BaseViewModel
import com.motivation.affirmations.ui.fragments.main.ui_state.HomeUiState
import com.motivation.affirmations.util.helpers.sounds_player.SoundCompletionListener
import com.motivation.affirmations.util.helpers.sounds_player.SoundPlayType
import com.motivation.affirmations.util.helpers.sounds_player.SoundPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 19.02.2024.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val soundRepository: SoundRepository,
    private val soundPlayer: SoundPlayer
) : BaseViewModel(), SoundCompletionListener {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _onSoundCompleted = MutableLiveData<Boolean>()
    val onSoundCompleted: LiveData<Boolean> = _onSoundCompleted

    private val favouriteSoundFlow = soundRepository.getFavouriteSoundsFlow()

    val soundProgress = soundPlayer.soundProgressFlow

    init {
        viewModelScope.launch {
            favouriteSoundFlow.collect { soundList ->
                _uiState.update { uiState ->
                    uiState.copy(
                        favouritesSoundsList = soundList
                    )
                }
            }
        }
    }

    fun updateSounds() {
        viewModelScope.launch {
            try {
                soundRepository.updateSoundsDb()
            } catch (error: Error) {
                _uiState.update {
                    it.copy(
                        errorMessage = error.message
                    )
                }
            }
        }
    }

    fun playSound(soundName: String) {
        viewModelScope.launch {
            soundPlayer.play(soundName, SoundPlayType.FULL, this@HomeViewModel)
        }
    }

    fun stopSound() {
        soundPlayer.stop()
    }

    override fun onSoundComplete() {
        _onSoundCompleted.value = true
    }

}
