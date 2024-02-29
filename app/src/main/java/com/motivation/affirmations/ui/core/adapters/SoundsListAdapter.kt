package com.motivation.affirmations.ui.core.adapters

import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motivation.affirmations.domain.model.Sound
import com.motivation.affirmations.util.helpers.images_loader.GlideImageLoader
import com.motivation.affirmations.util.helpers.sounds_player.SoundPlayer
import com.motivation.app.R
import com.motivation.app.databinding.ListItemSoundBinding

class SoundsListAdapter(
    private val onPlaybackClicked: (soundName: String, position: Int) -> Unit
) : ListAdapter<Sound, SoundsListAdapter.SoundViewHolder>(DiffCallback) {

    private val glideImageLoader = GlideImageLoader()
    private var selectedItemPosition: Int = -1

    private var currentPlayingImageView: ImageView? = null
    private var currentAnimationDrawable: AnimationDrawable? = null

    inner class SoundViewHolder(
        private val binding: ListItemSoundBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sound: Sound, position: Int) {
            binding.apply {
                tvSoundName.text = sound.titleEn
                cbFavourite.isChecked = sound.isFavorite
                cbFavourite.setOnCheckedChangeListener { _, isChecked ->
                    sound.isFavorite = isChecked
                }
                glideImageLoader.loadImage(sound.thumbnailName, ivSoundImage)

                if (position == selectedItemPosition) {
                    startPlaybackAnimation(ivPlaybackSound)
                } else {
                    stopPlaybackAnimation(ivPlaybackSound)
                }

                ivPlaybackSound.setOnClickListener {
                    if (position == selectedItemPosition) {
                        stopPlaybackAnimation(ivPlaybackSound)
                        onPlaybackClicked.invoke(sound.previewName, position)
                    } else {
                        if (selectedItemPosition != -1) {
                            notifyItemChanged(selectedItemPosition)
                        }
                        selectedItemPosition = position
                        onPlaybackClicked.invoke(sound.previewName, position)
                        notifyDataSetChanged()
                    }
                }
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
        holder.bind(currentSound, position)
    }

    private fun startPlaybackAnimation(imageView: ImageView) {
        currentPlayingImageView?.let { stopPlaybackAnimation(it) }
        val playbackImages = arrayOf(
            R.drawable.vector_playback_third_icon,
            R.drawable.vector_playback_second_icon,
            R.drawable.vector_playback_first_icon
        )
        val context = imageView.context
        val animationDrawable = AnimationDrawable().apply {
            for (imageResId in playbackImages) {
                ContextCompat.getDrawable(context, imageResId)?.let { drawable ->
                    addFrame(drawable, 1000)
                }
            }
            isOneShot = false
        }

        imageView.setImageDrawable(animationDrawable)
        animationDrawable.start()
        currentPlayingImageView = imageView
        currentAnimationDrawable = animationDrawable

        SoundPlayer.setOnCompletionListener {
            animationDrawable.stop()
            AppCompatResources.getDrawable(context, R.drawable.vector_playback_first_icon)?.let {
                imageView.setImageDrawable(it)
            }
        }
    }

    private fun stopPlaybackAnimation(imageView: ImageView) {
        currentAnimationDrawable?.stop()
        AppCompatResources.getDrawable(imageView.context, R.drawable.vector_playback_first_icon)?.let {
            imageView.setImageDrawable(it)
        }
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