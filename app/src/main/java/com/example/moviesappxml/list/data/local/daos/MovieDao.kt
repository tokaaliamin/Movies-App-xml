package com.example.moviesappxml.list.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesappxml.list.data.models.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Query("DELETE FROM movie")
    fun deleteAllMovies()

    @Query(
        "SELECT movie.id, movie.poster_path, movie.release_date, movie.title, " +
                "CASE WHEN favorite.movie_id IS NOT NULL THEN 1 ELSE 0 END AS isFavorite " +
                "FROM movie LEFT JOIN favorite ON movie.id = favorite.movie_id"
    )
    fun getAllMoviesWithFavoriteStatus(): PagingSource<Int, Movie>

}