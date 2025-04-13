package com.example.moviesappxml.details.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null
)

fun Genre.toDomainGenre() = com.example.moviesappxml.details.domain.models.Genre(
    id,
    name
)