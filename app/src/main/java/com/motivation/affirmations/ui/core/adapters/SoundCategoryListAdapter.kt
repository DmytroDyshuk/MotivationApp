package com.motivation.affirmations.ui.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motivation.affirmations.domain.model.SoundCategory
import com.motivation.app.R
import com.motivation.app.databinding.ListItemSoundCategoryBinding

class SoundCategoryListAdapter(
    private val onSoundCategoryClicked: (soundCategory: SoundCategory) -> Unit
) : ListAdapter<SoundCategory, SoundCategoryListAdapter.SoundCategoryViewHolder>(DiffCallback()) {

    inner class SoundCategoryViewHolder(
        private val binding: ListItemSoundCategoryBinding,
        private val onSoundCategoryClicked: (soundCategory: SoundCategory) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(soundCategory: SoundCategory) {
            binding.apply {
                tvSoundCategory.text = soundCategory.titleEn

                tvSoundCategory.setOnClickListener {
                    tvSoundCategory.setBackgroundResource(R.drawable.shape_rounded_blue_background)
                    onSoundCategoryClicked.invoke(soundCategory)
                }
            }
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SoundCategoryListAdapter.SoundCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemSoundCategoryBinding.inflate(layoutInflater, parent, false)
        return SoundCategoryViewHolder(binding, onSoundCategoryClicked)
    }

    override fun onBindViewHolder(holder: SoundCategoryListAdapter.SoundCategoryViewHolder, position: Int) {
        val currentCategory = getItem(position)
        holder.bind(currentCategory)
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