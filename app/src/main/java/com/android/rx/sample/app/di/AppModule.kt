package com.android.rx.sample.app.di

import com.android.rx.sample.app.data.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton @Provides
    fun provideMainRepository() = MainRepository()
}