package com.example.moviesappxml.list.data.repos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import androidx.paging.map
import com.example.moviesappxml.list.data.local.dataSources.FavoriteLocalDataSource
import com.example.moviesappxml.list.data.local.dataSources.MoviesListLocalDataSource
import com.example.moviesappxml.list.data.models.Favorite
import com.example.moviesappxml.list.data.models.Movie
import com.example.moviesappxml.list.data.models.toDomainMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesListRepo @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    private val moviesListLocalDataSource: MoviesListLocalDataSource,
    private val favoriteLocalDataSource: FavoriteLocalDataSource,
    private val moviesListRemoteMediator: RemoteMediator<Int, Movie>
) {

    // Create a reference to store the latest PagingSource
    private var currentPagingSource: PagingSource<Int, Movie>? = null

    // Create a PagingSourceFactory that stores the reference
    private val pagingSourceFactory = {
        moviesListLocalDataSource.fetchMoviesListWithFavoriteState().also {
            currentPagingSource = it
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getMovies(): Flow<PagingData<com.example.moviesappxml.list.domain.models.Movie>> {
        // Use RemoteMediator for regular movie list
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = moviesListRemoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { dataMovie ->
                dataMovie.toDomainMovie()
            }
        }
    }

    suspend fun addFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            favoriteLocalDataSource.addFavorite(Favorite(id, true))
            updateFavorites()
        }
    }

    suspend fun removeFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            favoriteLocalDataSource.removeFavorite(id)
            updateFavorites()
        }
    }

    private fun updateFavorites() {
        currentPagingSource?.invalidate()
    }

}