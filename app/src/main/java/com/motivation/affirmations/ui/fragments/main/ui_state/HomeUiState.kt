package com.motivation.affirmations.ui.fragments.main.ui_state

import com.motivation.affirmations.domain.model.Sound

data class HomeUiState(
    val favouritesSoundsList: List<Sound> = listOf(),
    val errorMessage: String? = null
)
