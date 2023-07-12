package com.example.pixabaypm.domain.model

data class PictureModel(
    val id: Long,
    val userName: String,
    val tags: String,
    val likesNr: Int,
    val downloadsNr: Int,
    val commentsNr: Int,
    val imageUrl: String,
    val largeImageUrl: String
)
