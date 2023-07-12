package com.example.pixabaypm.di

import com.example.pixabaypm.data.api.ApiService
import com.example.pixabaypm.domain.repository.PicturesRepository
import com.example.pixabaypm.domain.usecase.GetPicturesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService) = PicturesRepository(apiService)

    @Singleton
    @Provides
    fun providesUseCase(repository: PicturesRepository) = GetPicturesUseCase(repository)
}