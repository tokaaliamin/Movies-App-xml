package com.example.moviesappxml.list.domain.useCases

import androidx.paging.PagingData
import com.example.moviesappxml.list.data.repos.MoviesListRepo
import com.example.moviesappxml.list.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieListUseCase @Inject constructor(private val moviesListRepo: MoviesListRepo) {

    operator fun invoke(): Flow<PagingData<Movie>> {
        return moviesListRepo.getMovies()
    }

    suspend fun addFavorite(id: Int) {
        moviesListRepo.addFavorite(id)
    }

    suspend fun removeFavorite(id: Int) {
        moviesListRepo.removeFavorite(id)
    }
}