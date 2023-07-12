package com.example.pixabaypm.domain.usecase

import com.example.pixabaypm.domain.model.PictureModel
import com.example.pixabaypm.domain.repository.PicturesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetPicturesUseCase(private val repository: PicturesRepository) {
    fun execute(query: String): Flow<List<PictureModel>> = flow {
        emit(repository.getPictures(query))
    }.flowOn(Dispatchers.IO)
}