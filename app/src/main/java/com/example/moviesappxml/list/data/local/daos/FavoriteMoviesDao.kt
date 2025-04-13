package com.example.moviesappxml.list.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesappxml.list.data.models.Favorite

@Dao
interface FavoriteMoviesDao {
    @Query("SELECT * FROM favorite")
    fun fetchFavoritesList(): PagingSource<Int, Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE movie_id = :id")
    fun deleteFavorite(id: Int)

}