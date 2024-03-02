package com.motivation.affirmations.ui.core.adapters.sound_categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motivation.affirmations.domain.model.SoundCategory
import com.motivation.app.R
import com.motivation.app.databinding.ListItemCategoryBinding

class SoundCategoryListAdapter(
    private val onSoundCategoryClicked: (soundCategory: SoundCategory) -> Unit
) : ListAdapter<SoundCategory, SoundCategoryListAdapter.SoundCategoryViewHolder>(DiffCallback()) {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    init {
        selectItem(0)
    }

    inner class SoundCategoryViewHolder(
        private val binding: ListItemCategoryBinding,
        private val onSoundCategoryClicked: (soundCategory: SoundCategory) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(soundCategory: SoundCategory, position: Int) {
            binding.apply {
                tvSoundCategory.text = soundCategory.titleEn

                if (position == selectedPosition) {
                    tvSoundCategory.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                    tvSoundCategory.setBackgroundResource(R.drawable.shape_rounded_blue_background)
                } else {
                    tvSoundCategory.setTextColor(ContextCompat.getColor(binding.root.context, R.color.pickled_bluewood))
                    tvSoundCategory.setBackgroundResource(R.drawable.shape_rounded_light_blue_background)
                }

                tvSoundCategory.setOnClickListener {
                    selectItem(position)
                    onSoundCategoryClicked.invoke(soundCategory)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SoundCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemCategoryBinding.inflate(layoutInflater, parent, false)
        return SoundCategoryViewHolder(binding, onSoundCategoryClicked)
    }

    override fun onBindViewHolder(holder: SoundCategoryViewHolder, position: Int) {
        val currentCategory = getItem(position)
        holder.bind(currentCategory, position)
    }

    private fun selectItem(position: Int) {
        val previousSelectedPosition = selectedPosition
        selectedPosition = position
        if (previousSelectedPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(previousSelectedPosition)
        }
        notifyItemChanged(selectedPosition)
    }


    class DiffCallback : DiffUtil.ItemCallback<SoundCategory>() {

        override fun areItemsTheSame(oldItem: SoundCategory, newItem: SoundCategory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SoundCategory, newItem: SoundCategory): Boolean {
            return oldItem == newItem
        }
    }

}