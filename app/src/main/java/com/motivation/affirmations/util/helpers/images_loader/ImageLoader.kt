package com.motivation.affirmations.util.helpers.images_loader

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(imageName: String, target: ImageView)
}