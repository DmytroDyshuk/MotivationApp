package com.motivation.affirmations.ui.core.adapters

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
import com.motivation.affirmations.util.helpers.sounds_player.SoundPlayer
import com.motivation.app.R
import com.motivation.app.databinding.ItemAddFavouriteSoundBinding
import com.motivation.app.databinding.ListItemFavouriteSoundBinding

class FavouriteSoundsListAdapter(
    private val onSoundClicked: (soundName: String, position: Int) -> Unit,
    private val onAddSoundClicked: () -> Unit
) : ListAdapter<Sound, RecyclerView.ViewHolder>(diffCallback) {

    private val glideImageLoader = GlideImageLoader()

    private var selectedItemPosition: Int = -1

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
        private val binding: ListItemFavouriteSoundBinding,
        private val onSoundClicked: (soundName: String, position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sound: Sound, position: Int) {
            binding.apply {
                tvSoundName.text = sound.titleEn
                glideImageLoader.loadImage(sound.thumbnailName, ivBackgroundImage)

                if (position == selectedItemPosition) {
                    setPauseIconAndShowProgress(ivPlayPauseIcon, pbSoundPlayProgress)
                    SoundPlayer.setOnCompletionListener {
                        setPlayIconAndHideProgress(ivPlayPauseIcon, pbSoundPlayProgress)
                    }
                } else {
                    setPlayIconAndHideProgress(ivPlayPauseIcon, pbSoundPlayProgress)
                }

                ivPlayPauseIcon.setOnClickListener {
                    if (position == selectedItemPosition) {
                        setPlayIconAndHideProgress(ivPlayPauseIcon, pbSoundPlayProgress)
                        onSoundClicked.invoke(sound.soundName, position)
                    } else {
                        if (selectedItemPosition != -1) {
                            notifyItemChanged(selectedItemPosition)
                        }
                        selectedItemPosition = position
                        onSoundClicked.invoke(sound.soundName, position)
                        notifyDataSetChanged()
                    }
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

    private fun setPlayIconAndHideProgress(imageView: ImageView, progressBar: ProgressBar) {
        imageView.setImageDrawable(
            AppCompatResources.getDrawable(imageView.context, R.drawable.vector_play_icon)
        )
        hideProgress(progressBar)
    }

    private fun setPauseIconAndShowProgress(imageView:ImageView, progressBar: ProgressBar) {
        imageView.setImageDrawable(
            AppCompatResources.getDrawable(imageView.context, R.drawable.vector_pause_icon)
        )
        showProgress(progressBar)
    }

    private fun hideProgress(progressBar: ProgressBar) {
        progressBar.progress = 0
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgress(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
        SoundPlayer.setProgressListener {
            progressBar.progress = it
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