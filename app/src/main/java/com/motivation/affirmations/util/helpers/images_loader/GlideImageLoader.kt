package com.motivation.affirmations.util.helpers.images_loader

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.motivation.affirmations.util.Defaults

class GlideImageLoader : ImageLoader {
    override fun loadImage(imageName: String, target: ImageView) {
        Glide.with(target.context)
            .load(Defaults.SOUND_IMAGES_FOLDER_URL + imageName)
            .into(target)
    }
}