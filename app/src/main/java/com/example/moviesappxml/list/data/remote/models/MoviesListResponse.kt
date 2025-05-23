package com.example.moviesappxml.list.data.remote.models


import com.example.moviesappxml.list.data.models.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesListResponse(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val movies: List<Movie>,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)