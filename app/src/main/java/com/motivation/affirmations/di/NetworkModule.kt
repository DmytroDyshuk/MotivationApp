package com.motivation.affirmations.di

import com.motivation.affirmations.data.source.remote.api.SoundApi
import com.motivation.affirmations.data.source.remote.api.SoundCategoryApi
import com.motivation.affirmations.data.source.remote.api.SoundFileApi
import com.motivation.affirmations.util.Defaults
import com.motivation.app.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        val baseUrl = "${Defaults.SERVER_URL}/${Defaults.APPLICATION_ID}/${Defaults.API_KEY}/"
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideSoundApi(retrofit: Retrofit): SoundApi {
        return retrofit.create(SoundApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSoundCategoryApi(retrofit: Retrofit): SoundCategoryApi {
        return retrofit.create(SoundCategoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSoundFileApi(retrofit: Retrofit): SoundFileApi {
        return retrofit.create(SoundFileApi::class.java)
    }
}