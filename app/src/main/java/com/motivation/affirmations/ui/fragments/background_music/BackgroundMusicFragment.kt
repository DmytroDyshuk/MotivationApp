package com.motivation.affirmations.ui.fragments.background_music

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.motivation.affirmations.ui.core.adapters.SoundCategoryListAdapter
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

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentBackgroundMusicBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshSoundCategories()

        val adapter = SoundCategoryListAdapter(onSoundCategoryClicked = {
            Toast.makeText(view.context, "OnCategoryClicked ${it.titleEn}", Toast.LENGTH_SHORT).show()
        })

        binding.rvSoundsCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvSoundsCategories.adapter = adapter
        val spaceDecoration = SpaceItemDecoration(
            top = 54,
            left = 0,
            right = 18,
            bottom = 54,
            addSpaceAboveFirstItem = true,
            addSpaceBelowLastItem = false
        )

        binding.rvSoundsCategories.addItemDecoration(spaceDecoration)

        viewModel.viewModelScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    adapter.submitList(it.soundCategoriesList)
                }
            }
        }
    }
}
