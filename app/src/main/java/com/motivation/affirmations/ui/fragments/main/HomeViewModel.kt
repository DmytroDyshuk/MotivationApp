package com.motivation.affirmations.ui.fragments.main

import androidx.lifecycle.viewModelScope
import com.motivation.affirmations.domain.repository.SoundRepository
import com.motivation.affirmations.ui.core.BaseViewModel
import com.motivation.affirmations.ui.fragments.main.ui_state.HomeUiState
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
    private val soundRepository: SoundRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val favouriteSoundFlow = soundRepository.getFavouriteSoundsFlow()

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

}
