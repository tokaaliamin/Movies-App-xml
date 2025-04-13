package com.example.moviesappxml.list.data.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Movie(
    @PrimaryKey
    @SerialName("id")
    val id: Int? = null,
    @ColumnInfo(name = "poster_path")
    @SerialName("poster_path")
    val posterPath: String? = null,
    @ColumnInfo(name = "release_date")
    @SerialName("release_date")
    val releaseDate: String? = null,
    @ColumnInfo(name = "title")
    @SerialName("title")
    val title: String? = null,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false
) {
    constructor(
        id: Int?,
        posterPath: String?,
        releaseDate: String?,
        title: String?,
        isFavorite: Int
    ) : this(
        id, posterPath, releaseDate, title, isFavorite == 1
    )
}

fun Movie.toDomainMovie() = com.example.moviesappxml.list.domain.models.Movie(
    id ?: 0,
    posterPath,
    releaseDate,
    title,
    isFavorite
)