package com.example.pixabaypm.data.api

import com.example.pixabaypm.data.api.dto.GetPicturesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/")
    suspend fun getPictures(
        @Query("q") query: String,
        @Query("image_type") imageType: String = "photo"
    ): GetPicturesResponseDto
}