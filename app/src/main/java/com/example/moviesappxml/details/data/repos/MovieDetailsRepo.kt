package com.example.moviesappxml.details.data.repos

import com.example.moviesappxml.details.data.remote.dataSources.MovieDetailsDataSource
import com.example.moviesappxml.details.data.remote.models.toDomainMovieDetails
import com.example.moviesappxml.details.domain.models.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailsRepo @Inject constructor(private val movieDataRemoteDataSource: MovieDetailsDataSource) {

    suspend fun fetchMovieDetails(movieId: Int): Result<MovieDetails> {
        return withContext(Dispatchers.IO) {
            try {
                val result = movieDataRemoteDataSource.fetchMovieDetails(movieId)
                Result.success(result.toDomainMovieDetails())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}