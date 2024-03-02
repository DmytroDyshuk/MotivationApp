package com.motivation.affirmations.ui.fragments.main

import  android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.motivation.affirmations.domain.model.Sound
import com.motivation.affirmations.ui.core.adapters.favourite_sounds.FavouriteSoundsListAdapter
import com.motivation.affirmations.ui.core.adapters.SpaceItemDecoration
import com.motivation.affirmations.ui.core.adapters.favourite_sounds.FavouriteSoundItemClickListener
import com.motivation.affirmations.ui.fragments.ViewBindingFragment
import com.motivation.app.R
import com.motivation.app.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 19.02.2024.
 */
@AndroidEntryPoint
class HomeFragment : ViewBindingFragment<FragmentHomeBinding>(), FavouriteSoundItemClickListener {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var favouriteSoundsAdapter: FavouriteSoundsListAdapter

    private var isTuneExpanded = false
    private var currentPlayingPosition = -1

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateSounds()
        initSoundsListAdapter()
    }

    override fun onStop() {
        super.onStop()
        isTuneExpanded = !isTuneExpanded
        viewModel.resetPlayer()
    }

    override fun setListeners() {
        super.setListeners()
        binding.btnSelectTune.setOnClickListener {
            view?.context?.let { ctx ->
                toggleTuneIconAndAnimation(ctx)
            }
        }
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.viewModelScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    favouriteSoundsAdapter.submitList(it.favouritesSoundsList)
                }
            }
        }

        viewModel.viewModelScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.soundProgress.collect {
                    if (favouriteSoundsAdapter.currentList.isNotEmpty()) {
                        favouriteSoundsAdapter.updateSoundProgress(currentPlayingPosition, it)
                        favouriteSoundsAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        viewModel.onSoundCompleted.observe(viewLifecycleOwner) {
            if (it == true) {
                favouriteSoundsAdapter.updateUiOnSoundCompletion()
            }
        }
    }

    private fun initSoundsListAdapter() {
        favouriteSoundsAdapter = FavouriteSoundsListAdapter(this)
        val spaceDecoration = SpaceItemDecoration(
            top = 0,
            left = 16,
            right = 16,
            bottom = 0,
            addSpaceAboveFirstItem = true,
            addSpaceBelowLastItem = false
        )
        binding.apply {
            rvFavouriteSounds.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvFavouriteSounds.adapter = favouriteSoundsAdapter
            rvFavouriteSounds.addItemDecoration(spaceDecoration)
        }
    }

    private fun toggleTuneIconAndAnimation(context: Context) {
        setTuneIcon(context)
        moveButtonsWithAnimation()
        showRecyclerViewSoundsWithAnimation()
        isTuneExpanded = !isTuneExpanded
    }

    private fun setTuneIcon(context: Context) {
        val iconRes = if (isTuneExpanded) R.drawable.vector_disabled_tune_icon else R.drawable.vector_enabled_tune_icon
        binding.btnSelectTune.icon = ContextCompat.getDrawable(context, iconRes)
    }

    private fun moveButtonsWithAnimation() {
        val translation = if (isTuneExpanded) 0f else -200f
        ObjectAnimator.ofFloat(binding.llTuneButtons, "translationY", translation).apply {
            duration = 450
            start()
        }
    }

    private fun showRecyclerViewSoundsWithAnimation() {
        val startAlpha = if (isTuneExpanded) 1f else 0f
        val endAlpha = if (isTuneExpanded) 0f else 1f
        ObjectAnimator.ofFloat(binding.rvFavouriteSounds, View.ALPHA, startAlpha, endAlpha).apply {
            duration = 450
            start()
        }
        if (!isTuneExpanded) {
            binding.rvFavouriteSounds.visibility = View.VISIBLE
        } else {
            binding.rvFavouriteSounds.visibility = View.GONE
        }
    }

    override fun onSoundClicked(sound: Sound, position: Int) {
        viewModel.playSound(sound.soundName)
        if (position != currentPlayingPosition) {
            favouriteSoundsAdapter.notifyDataSetChanged()
        }
        currentPlayingPosition = position
    }

    override fun onAddSoundClicked() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBackgroundMusicFragment())
    }
}