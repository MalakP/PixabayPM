package com.example.pixabaypm.di

import android.content.Context
import com.example.pixabaypm.PixApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): PixApplication =
        app as PixApplication

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext app: Context): Context = app.applicationContext
}