package com.example.moviesappxml.list.domain.models


import androidx.room.Entity
import com.example.moviesappxml.common.domain.models.ImageSize
import com.example.moviesappxml.common.domain.useCases.ImagesUseCase

@Entity
data class Movie(
    val id: Int,
    val posterSuffix: String? = null,
    val releaseDate: String? = null,
    val title: String? = null
)

fun Movie.toUiMovie(): com.example.moviesappxml.list.ui.models.Movie {
    return com.example.moviesappxml.list.ui.models.Movie(
        id,
        title,
        ImagesUseCase().invoke(posterSuffix, ImageSize.POSTER),
        releaseDate
    )
}