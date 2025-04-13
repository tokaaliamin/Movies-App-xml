package com.example.moviesappxml.list.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesappxml.list.data.local.daos.FavoriteMoviesDao
import com.example.moviesappxml.list.data.local.daos.MovieDao
import com.example.moviesappxml.list.data.models.Favorite
import com.example.moviesappxml.list.data.models.Movie

@Database(entities = [Movie::class, Favorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun favoriteMoviesDao(): FavoriteMoviesDao
}