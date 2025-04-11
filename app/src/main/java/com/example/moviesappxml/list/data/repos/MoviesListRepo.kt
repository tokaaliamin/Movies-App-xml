package com.example.moviesappxml.list.data.repos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import androidx.paging.map
import com.example.moviesappxml.list.data.local.dataSources.MoviesListLocalDataSource
import com.example.moviesappxml.list.data.models.Movie
import com.example.moviesappxml.list.data.models.toDomainMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesListRepo @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    private val moviesListLocalDataSource: MoviesListLocalDataSource,
    private val moviesListRemoteMediator: RemoteMediator<Int, Movie>
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getMovies(): Flow<PagingData<com.example.moviesappxml.list.domain.models.Movie>> {
        // Use RemoteMediator for regular movie list
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = moviesListRemoteMediator
        ) {
            moviesListLocalDataSource.fetchMoviesList()
        }.flow.map { pagingData ->
            pagingData.map { dataMovie -> dataMovie.toDomainMovie() }
        }
    }

}