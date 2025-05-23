package com.example.moviesappxml.list.data.local.dataSources

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.moviesappxml.list.data.local.database.AppDatabase
import com.example.moviesappxml.list.data.models.Movie
import javax.inject.Inject

class MoviesListLocalDataSource @Inject constructor(private val database: AppDatabase) {

    fun fetchMoviesListWithFavoriteState(): PagingSource<Int, Movie> {
        return database.movieDao().getAllMoviesWithFavoriteStatus()
    }


    fun cacheMovies(movies: List<Movie>) {
        database.movieDao().insertAll(movies)
    }

    fun clearMovies() {
        database.movieDao().deleteAllMovies()
    }


    suspend fun updateMoviesData(isRefresh: Boolean, movies: List<Movie>) {
        database.withTransaction {
            if (isRefresh) {
                clearMovies()
            }

            // Insert new users into database, which invalidates the
            // current PagingData, allowing Paging to present the updates
            // in the DB.
            cacheMovies(movies)
        }
    }

}