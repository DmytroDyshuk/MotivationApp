package com.motivation.affirmations.ui.fragments.background_music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.motivation.affirmations.ui.core.adapters.SoundCategoryListAdapter
import com.motivation.affirmations.ui.core.adapters.SoundsListAdapter
import com.motivation.affirmations.ui.core.adapters.SpaceItemDecoration
import com.motivation.affirmations.ui.fragments.ViewBindingFragment
import com.motivation.app.databinding.FragmentBackgroundMusicBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 19.02.2024.
 */
@AndroidEntryPoint
class BackgroundMusicFragment : ViewBindingFragment<FragmentBackgroundMusicBinding>() {

    private val viewModel by viewModels<BackgroundMusicViewModel>()

    private lateinit var categoryListAdapter: SoundCategoryListAdapter
    private lateinit var soundsListAdapter: SoundsListAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentBackgroundMusicBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refreshSoundCategories()
        initCategoriesListAdapter()
        initSoundsListAdapter()
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.viewModelScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.soundCategories.collect {
                    categoryListAdapter.submitList(it)
                }
            }
        }

        viewModel.viewModelScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.soundList.collect { soundsList ->
                    soundsListAdapter.submitList(soundsList)
                    if (soundsList.isEmpty()) {
                        binding.tvNoSongs.visibility = View.VISIBLE
                        binding.rvSounds.visibility = View.INVISIBLE
                    } else {
                        binding.tvNoSongs.visibility = View.GONE
                        binding.rvSounds.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun setListeners() {
        super.setListeners()
        binding.topAppBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }
    }

    private fun initSoundsListAdapter() {
        soundsListAdapter = SoundsListAdapter()
        binding.rvSounds.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSounds.adapter = soundsListAdapter
        //TODO: add divider
    }

    private fun initCategoriesListAdapter() {
        categoryListAdapter = SoundCategoryListAdapter(
            onSoundCategoryClicked = {
                viewModel.getSoundsByCategory(it.id)
            }
        )

        binding.rvSoundsCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvSoundsCategories.adapter = categoryListAdapter
        val spaceDecoration = SpaceItemDecoration(
            top = 54,
            left = 24,
            right = 18,
            bottom = 54,
            addSpaceAboveFirstItem = true,
            addSpaceBelowLastItem = false
        )
        binding.rvSoundsCategories.addItemDecoration(spaceDecoration)
    }
}
