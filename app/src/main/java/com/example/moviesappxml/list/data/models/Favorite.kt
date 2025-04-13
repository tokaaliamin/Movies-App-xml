package com.example.moviesappxml.list.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "favorite")
@Serializable
data class Favorite(
    @PrimaryKey
    @SerialName("movie_id")
    @ColumnInfo(name = "movie_id")
    val movieId: Int? = null,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = true
)