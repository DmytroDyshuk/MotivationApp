package com.motivation.affirmations.ui.fragments.main

import androidx.lifecycle.viewModelScope
import com.motivation.affirmations.domain.repository.SoundRepository
import com.motivation.affirmations.ui.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 19.02.2024.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val soundRepository: SoundRepository
) : BaseViewModel() {

    fun updateSounds() {
        viewModelScope.launch {
            try {
                soundRepository.updateSoundsDb()
            } catch (exception: Exception) {
                Timber.tag("HomeViewModel").e("exception: ${exception.message}")
            }
        }
    }

}
