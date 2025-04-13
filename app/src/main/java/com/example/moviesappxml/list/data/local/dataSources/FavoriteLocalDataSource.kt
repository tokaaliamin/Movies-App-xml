package com.example.moviesappxml.list.data.local.dataSources

import com.example.moviesappxml.list.data.local.database.AppDatabase
import com.example.moviesappxml.list.data.models.Favorite
import javax.inject.Inject

class FavoriteLocalDataSource @Inject constructor(private val database: AppDatabase) {
    fun addFavorite(favorite: Favorite) {
        database.favoriteMoviesDao().insertFavorite(favorite)
    }

    fun removeFavorite(id: Int) {
        database.favoriteMoviesDao().deleteFavorite(id)
    }
}