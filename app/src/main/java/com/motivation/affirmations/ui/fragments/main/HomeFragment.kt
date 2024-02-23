package com.motivation.affirmations.ui.fragments.main

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.motivation.affirmations.ui.fragments.ViewBindingFragment
import com.motivation.app.R
import com.motivation.app.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 19.02.2024.
 */
@AndroidEntryPoint
class HomeFragment : ViewBindingFragment<FragmentMainBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    private var isTuneExpanded = false

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    override fun setListeners() {
        super.setListeners()
        binding.btnSelectTune.setOnClickListener {
            view?.context?.let { ctx ->
                toggleTuneIconAndAnimation(ctx)
            }
        }
    }

    private fun toggleTuneIconAndAnimation(context: Context) {
        val iconRes = if (isTuneExpanded) R.drawable.vector_disabled_tune_icon else R.drawable.vector_enabled_tune_icon
        binding.btnSelectTune.icon = ContextCompat.getDrawable(context, iconRes)
        val translation = if (isTuneExpanded) 0f else -250f
        moveViewWithAnimation(translation)
        isTuneExpanded = !isTuneExpanded
    }

    private fun moveViewWithAnimation(value: Float) {
        ObjectAnimator.ofFloat(binding.llTuneButtons, "translationY", value).apply {
            duration = 450
            start()
        }
    }
}