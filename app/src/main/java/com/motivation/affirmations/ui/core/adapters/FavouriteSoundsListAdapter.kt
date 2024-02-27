package com.motivation.affirmations.ui.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motivation.affirmations.domain.model.Sound
import com.motivation.app.databinding.ItemAddFavouriteSoundBinding
import com.motivation.app.databinding.ListItemFavouriteSoundBinding

class FavouriteSoundsListAdapter(
    private val onSoundClicked: () -> Unit,
    private val onAddSoundClicked: () -> Unit
) : ListAdapter<Sound, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_STATIC -> {
                val binding = ItemAddFavouriteSoundBinding.inflate(layoutInflater, parent, false)
                StaticViewHolder(binding, onAddSoundClicked)
            }
            else -> {
                val binding = ListItemFavouriteSoundBinding.inflate(layoutInflater, parent, false)
                SoundsViewHolder(binding, onSoundClicked)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position > 0) {
            val sound = getItem(position - 1)
            (holder as? SoundsViewHolder)?.bind(sound)
        } else {
            (holder as? StaticViewHolder)?.bind()
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_STATIC else VIEW_TYPE_NORMAL
    }

    inner class SoundsViewHolder(
        private val binding: ListItemFavouriteSoundBinding,
        private val onSoundClicked: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sound: Sound) {
            binding.apply {
                //TODO: add music image
//                Glide.with(itemView)
//                    .load(sound.thumbnailName)
//                    .into(ivBackgroundImage)

                tvSoundName.text = sound.soundName

                mcvSoundIcon.setOnClickListener {
                    onSoundClicked.invoke()
                }
            }
        }
    }

    inner class StaticViewHolder(
        private val binding: ItemAddFavouriteSoundBinding,
        private val onAddSoundClicked: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                mcvAddSoundIcon.setOnClickListener {
                    onAddSoundClicked.invoke()
                }
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_STATIC = 0
        private const val VIEW_TYPE_NORMAL = 1

        private val diffCallback = object : DiffUtil.ItemCallback<Sound>() {

            override fun areItemsTheSame(oldItem: Sound, newItem: Sound): Boolean {
                return oldItem.soundName == newItem.soundName
            }

            override fun areContentsTheSame(oldItem: Sound, newItem: Sound): Boolean {
                return oldItem == newItem
            }
        }
    }

}