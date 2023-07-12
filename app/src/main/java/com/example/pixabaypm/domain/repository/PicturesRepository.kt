package com.example.pixabaypm.domain.repository

import com.example.pixabaypm.data.api.ApiService
import com.example.pixabaypm.data.api.mapper.toImageModel
import com.example.pixabaypm.domain.model.PictureModel

class PicturesRepository(private val apiService: ApiService) {
    suspend fun getPictures(query: String): List<PictureModel> =
        apiService.getPictures(query).hits.map { it.toImageModel() }
}