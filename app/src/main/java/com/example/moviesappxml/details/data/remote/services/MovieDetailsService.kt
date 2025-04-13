package com.example.moviesappxml.details.data.remote.services

import com.example.moviesappxml.details.data.remote.models.MovieDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsService {
    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): MovieDetails
}
