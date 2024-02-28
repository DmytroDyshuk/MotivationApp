package com.motivation.affirmations.ui.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motivation.affirmations.domain.model.Sound
import com.motivation.affirmations.util.helpers.images_loader.GlideImageLoader
import com.motivation.app.databinding.ListItemSoundBinding

class SoundsListAdapter : ListAdapter<Sound, SoundsListAdapter.SoundViewHolder>(DiffCallback) {

    private val glideImageLoader = GlideImageLoader()

    inner class SoundViewHolder(
        private val binding: ListItemSoundBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sound: Sound) {
            binding.apply {
                tvSoundName.text = sound.titleEn
                cbFavourite.isChecked = sound.isFavorite
                cbFavourite.setOnCheckedChangeListener { _, isChecked ->
                    sound.isFavorite = isChecked
                }
                glideImageLoader.loadImage(sound.thumbnailName, ivSoundImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemSoundBinding.inflate(layoutInflater, parent, false)
        return SoundViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SoundViewHolder, position: Int) {
        val currentSound = getItem(position)
        holder.bind(currentSound)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Sound>() {
        override fun areItemsTheSame(oldItem: Sound, newItem: Sound): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Sound, newItem: Sound): Boolean {
            return oldItem == newItem
        }
    }
}