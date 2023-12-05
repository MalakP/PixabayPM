package com.example.pixabaypm.ui

/**
 * Created by Piotr.Malak on 17/10/2023.

 */
sealed interface UIEvent{
    data class QueryChanged(val query: String): UIEvent
    data class SearchImages(val query: String): UIEvent
}