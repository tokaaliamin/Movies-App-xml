package com.example.moviesappxml.common.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null
)

fun Genre.toDomainGenre() = com.example.moviesappxml.common.domain.models.Genre(
    id,
    name
)