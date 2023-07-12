package com.example.pixabaypm.data.api.mapper

import com.example.pixabaypm.data.api.dto.PictureDto
import com.example.pixabaypm.domain.model.PictureModel

fun PictureDto.toImageModel(): PictureModel {
    return PictureModel(
        id = this.id,
        userName = this.user.orEmpty(),
        tags = this.tags.orEmpty(),
        likesNr = this.likes ?: 0,
        downloadsNr = this.downloads ?: 0,
        commentsNr = this.comments ?: 0,
        imageUrl = this.previewURL.orEmpty(),
        largeImageUrl = this.largeImageURL.orEmpty()
    )
}