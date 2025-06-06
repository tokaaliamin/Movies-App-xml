package com.example.moviesappxml.details.data.dataSources

import com.example.moviesappxml.details.data.remote.dataSources.MovieDetailsDataSource
import com.example.moviesappxml.details.data.remote.models.MovieDetails

class FakeMovieDetailsDataSource : MovieDetailsDataSource {
    override suspend fun fetchMovieDetails(movieId: Int): MovieDetails {
        if (movieId == 1) {
            //valid movie id
            return MovieDetails(id = movieId, title = "Movie")
        } else {
            //invalid id
            throw Throwable(message = "Not found")
        }
    }
}