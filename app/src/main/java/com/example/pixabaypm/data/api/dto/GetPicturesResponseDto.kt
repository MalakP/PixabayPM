package com.example.pixabaypm.data.api.dto

data class GetPicturesResponseDto(
    val total: Int,
    val totalHits: Int,
    val hits: List<PictureDto>
)
