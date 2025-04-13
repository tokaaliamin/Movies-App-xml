package com.example.moviesappxml.details.domain.useCases

import com.example.moviesappxml.details.data.repos.MovieDetailsRepo
import com.example.moviesappxml.details.domain.models.MovieDetails
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(private val movieDetailsRepo: MovieDetailsRepo) {

    suspend operator fun invoke(movieId: Int): Result<MovieDetails> {
        return movieDetailsRepo.fetchMovieDetails(movieId)
    }
}