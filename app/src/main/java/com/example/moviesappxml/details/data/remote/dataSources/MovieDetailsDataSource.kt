package com.example.moviesappxml.details.data.remote.dataSources

import com.example.moviesappxml.details.data.remote.models.MovieDetails
import com.example.moviesappxml.details.data.remote.services.MovieDetailsService
import javax.inject.Inject

class MovieDetailsRemoteDataSource @Inject constructor(private val movieDetailsService: MovieDetailsService) :
    MovieDetailsDataSource {
    override suspend fun fetchMovieDetails(movieId: Int): MovieDetails {
        return movieDetailsService.fetchMovieDetails(movieId)
    }
}

interface MovieDetailsDataSource {
    suspend fun fetchMovieDetails(movieId: Int): MovieDetails
}