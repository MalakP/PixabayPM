package com.example.pixabaypm.ui

import com.example.pixabaypm.domain.model.PictureModel

data class SearchScreenState(

    val isLoading: Boolean = false,

    val imagesFetched: List<PictureModel> = listOf(),

    val error: String = "",

    val query: String = ""
)