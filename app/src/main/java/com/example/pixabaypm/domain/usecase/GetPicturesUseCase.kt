package com.example.pixabaypm.domain.usecase

import com.example.pixabaypm.domain.model.PictureModel
import com.example.pixabaypm.domain.repository.PicturesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPicturesUseCase @Inject constructor() {
    @Inject
    lateinit var repository: PicturesRepository
    operator fun invoke(query: String): Flow<List<PictureModel>> = flow {
        emit(repository.getPictures(query))
    }.flowOn(Dispatchers.IO)
}