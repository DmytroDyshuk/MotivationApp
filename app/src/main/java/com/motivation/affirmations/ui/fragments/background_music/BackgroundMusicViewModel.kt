package com.motivation.affirmations.ui.fragments.background_music

import androidx.lifecycle.viewModelScope
import com.motivation.affirmations.domain.repository.SoundCategoryRepository
import com.motivation.affirmations.ui.core.BaseViewModel
import com.motivation.affirmations.ui.fragments.background_music.ui_state.BackgroundMusicUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 19.02.2024.
 */
@HiltViewModel
class BackgroundMusicViewModel @Inject constructor(
    private val soundCategoryRepository: SoundCategoryRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(BackgroundMusicUiState())
    val uiState: StateFlow<BackgroundMusicUiState> = _uiState.asStateFlow()

    private val soundCategoriesFlow = soundCategoryRepository.getSoundCategoriesFlow()

    init {
        viewModelScope.launch {
            soundCategoriesFlow.collect { soundCategoriesList ->
                _uiState.update {
                    it.copy(
                        soundCategoriesList = soundCategoriesList
                    )
                }
            }
        }
    }

    fun refreshSoundCategories() {
        viewModelScope.launch {
            try {
                soundCategoryRepository.refreshSoundCategories()
            } catch (exception: Exception) {
                Timber.tag("BackgroundMusicViewModel").e("e: %s", exception.message)
            }
        }
    }
}
