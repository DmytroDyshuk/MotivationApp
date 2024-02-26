package com.motivation.affirmations.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.motivation.affirmations.ui.ViewBindingFragment
import com.motivation.app.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 19.02.2024.
 */
@AndroidEntryPoint
class MainFragment : ViewBindingFragment<FragmentMainBinding>() {

    private val viewModel by viewModels<MainViewModel>()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentMainBinding.inflate(inflater, container, false)
}
