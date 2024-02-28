package com.motivation.affirmations.ui.fragments.background_music

import androidx.lifecycle.viewModelScope
import com.motivation.affirmations.domain.model.Sound
import com.motivation.affirmations.domain.model.SoundCategory
import com.motivation.affirmations.domain.repository.SoundCategoryRepository
import com.motivation.affirmations.domain.repository.SoundRepository
import com.motivation.affirmations.ui.core.BaseViewModel
import com.motivation.affirmations.ui.fragments.background_music.ui_state.BackgroundMusicUiState
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
class BackgroundMusicViewModel @Inject constructor(
    private val soundCategoryRepository: SoundCategoryRepository,
    private val soundRepository: SoundRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(BackgroundMusicUiState())
    val uiState: StateFlow<BackgroundMusicUiState> = _uiState.asStateFlow()

    private val _soundCategories: MutableStateFlow<List<SoundCategory>> = MutableStateFlow(listOf())
    val soundCategories: StateFlow<List<SoundCategory>> = _soundCategories

    private val _soundList: MutableStateFlow<List<Sound>> = MutableStateFlow(listOf())
    val soundList: StateFlow<List<Sound>> = _soundList

    private val soundCategoriesFlow = soundCategoryRepository.getSoundCategoriesFlow()

    init {
        viewModelScope.launch {
            soundCategoriesFlow.collect { soundCategoriesList ->
                _soundCategories.emit(soundCategoriesList)
            }
        }
    }

    fun refreshSoundCategories() {
        viewModelScope.launch {
            try {
                soundCategoryRepository.refreshSoundCategories()
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        exceptionMessage = exception.message
                    )
                }
            }
        }
    }

    fun getSoundsByCategory(categoryId: Int = 1) {
        viewModelScope.launch {
            try {
                val soundsListByCategory = soundRepository.getSoundsListByCategory(categoryId)
                _soundList.emit(soundsListByCategory)
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        exceptionMessage = exception.message
                    )
                }
            }
        }
    }

    fun saveSoundsToFavourites(soundsList: List<Sound>) {
        viewModelScope.launch {
            try {
                soundRepository.addSoundsToFavourites(soundsList)
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