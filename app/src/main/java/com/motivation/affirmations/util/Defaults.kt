package com.motivation.affirmations.util

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 26.04.2022.
 */
object Defaults {
    const val APPLICATION_ID = "BAA5FA22-AD7D-4B2F-FF7A-6520045EDE00"
    const val API_KEY = "7EC697EE-49BF-4225-B678-B9EA0D222F4C"
    const val SERVER_URL = "https://api.backendless.com"

    const val SOUND_IMAGES_FOLDER_URL = "${SERVER_URL}/${APPLICATION_ID}/${API_KEY}/files/sounds_images/"
    const val SOUND_FILES_FOLDER_URL = "${SERVER_URL}/${APPLICATION_ID}/${API_KEY}/files/sounds/"
    const val SOUND_PREVIEW_FOLDER_URL = "${SERVER_URL}/${APPLICATION_ID}/${API_KEY}/files/sounds_preview/"
}
