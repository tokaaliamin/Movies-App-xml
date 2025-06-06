package com.example.moviesappxml.details.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("genres")
    val genres: List<Genre>? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("popularity")
    val popularity: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null,
    @SerialName("runtime")
    val runtime: Int? = null
)

fun MovieDetails.toDomainMovieDetails() =
    com.example.moviesappxml.details.domain.models.MovieDetails(
        backdropPath,
        genres?.map { it.toDomainGenre() },
        id ?: 0,
        title,
        overview,
        posterPath,
        voteAverage,
        runtime
    )