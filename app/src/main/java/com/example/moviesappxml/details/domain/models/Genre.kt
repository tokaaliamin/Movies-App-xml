package com.example.moviesappxml.details.domain.models


import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int? = null,
    val name: String? = null
)

fun Genre.toUiGenre() = com.example.moviesappxml.details.ui.models.Genre(
    id,
    name
)