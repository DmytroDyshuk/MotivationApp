package com.motivation.affirmations.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 17.02.2022.
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Qualifier
    annotation class Local

    @Qualifier
    annotation class Remote
}
