package com.example.moviesappxml.list.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Favorite(
    @PrimaryKey
    @SerialName("id")
    val id: Int? = null
)