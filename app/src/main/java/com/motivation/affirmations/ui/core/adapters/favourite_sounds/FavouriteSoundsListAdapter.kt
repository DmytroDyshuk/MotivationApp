package com.motivation.affirmations.ui.core.adapters.favourite_sounds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motivation.affirmations.domain.model.Sound
import com.motivation.affirmations.util.helpers.images_loader.GlideImageLoader
import com.motivation.app.R
import com.motivation.app.databinding.ItemAddFavouriteSoundBinding
import com.motivation.app.databinding.ListItemFavouriteSoundBinding

class FavouriteSoundsListAdapter(
    private val soundItemClickListener: FavouriteSoundItemClickListener,
) : ListAdapter<Sound, RecyclerView.ViewHolder>(diffCallback) {

    private val glideImageLoader = GlideImageLoader()

    private var selectedItemPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_STATIC -> {
                val binding = ItemAddFavouriteSoundBinding.inflate(layoutInflater, parent, false)
                StaticViewHolder(binding)
            }

            else -> {
                val binding = ListItemFavouriteSoundBinding.inflate(layoutInflater, parent, false)
                SoundsViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position > 0) {
            val sound = getItem(position - 1)
            (holder as? SoundsViewHolder)?.bind(sound, position - 1)
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
        private val binding: ListItemFavouriteSoundBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sound: Sound, position: Int) {
            binding.apply {
                tvSoundName.text = sound.titleEn
                glideImageLoader.loadImage(sound.thumbnailName, ivBackgroundImage)
                pbSoundPlayProgress.progress = sound.soundProgress

                if (position == selectedItemPosition) {
                    setPauseIconAndShowProgress(ivPlayPauseIcon, pbSoundPlayProgress)
                } else {
                    setPlayIconAndHideProgress(ivPlayPauseIcon, pbSoundPlayProgress)
                }

                clFavouriteSound.setOnClickListener {
                    soundItemClickListener.onSoundClicked(sound, position)
                    if (position == selectedItemPosition) {
                        setPlayIconAndHideProgress(ivPlayPauseIcon, pbSoundPlayProgress)
                    } else {
                        if (selectedItemPosition != -1) {
                            notifyItemChanged(selectedItemPosition)
                        }
                        selectedItemPosition = position
                        setPauseIconAndShowProgress(ivPlayPauseIcon, pbSoundPlayProgress)
                    }
                }
            }
        }
    }

    inner class StaticViewHolder(
        private val binding: ItemAddFavouriteSoundBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                clAddSound.setOnClickListener {
                    soundItemClickListener.onAddSoundClicked()
                }
            }
        }
    }

    fun updateUiOnSoundCompletion() {
        selectedItemPosition = -1
        notifyDataSetChanged()
    }

    fun updateSoundProgress(position: Int, progress: Int) {
        if (position != -1) {
            getItem(position)?.let {
                it.soundProgress = progress
            }
        }
    }

    private fun setPlayIconAndHideProgress(imageView: ImageView, progressBar: ProgressBar) {
        imageView.setImageDrawable(
            AppCompatResources.getDrawable(imageView.context, R.drawable.vector_play_icon)
        )
        progressBar.progress = 0
        progressBar.visibility = View.INVISIBLE
    }

    private fun setPauseIconAndShowProgress(imageView: ImageView, progressBar: ProgressBar) {
        imageView.setImageDrawable(
            AppCompatResources.getDrawable(imageView.context, R.drawable.vector_pause_icon)
        )
        progressBar.visibility = View.VISIBLE
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