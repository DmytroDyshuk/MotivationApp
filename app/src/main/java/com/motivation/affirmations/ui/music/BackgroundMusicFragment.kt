package com.motivation.affirmations.ui.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.motivation.affirmations.ui.ViewBindingFragment
import com.motivation.app.databinding.FragmentBackgroundMusicBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 19.02.2024.
 */
@AndroidEntryPoint
class BackgroundMusicFragment : ViewBindingFragment<FragmentBackgroundMusicBinding>() {

    private val viewModel by viewModels<BackgroundMusicViewModel>()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentBackgroundMusicBinding.inflate(inflater, container, false)
}
