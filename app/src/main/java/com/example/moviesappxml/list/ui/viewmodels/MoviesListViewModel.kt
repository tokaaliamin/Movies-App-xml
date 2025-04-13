package com.example.moviesappxml.list.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.moviesappxml.list.domain.models.toUiMovie
import com.example.moviesappxml.list.domain.useCases.MovieListUseCase
import com.example.moviesappxml.list.ui.actions.MoviesListUiActions
import com.example.moviesappxml.list.ui.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(private val useCase: MovieListUseCase) : ViewModel() {

    private var _moviesListFlow: Flow<PagingData<Movie>> = useCase.invoke()
        .map { it.map { domainMovie -> domainMovie.toUiMovie() } }
        .cachedIn(viewModelScope)
    val moviesListFlow: Flow<PagingData<Movie>>
        get() = _moviesListFlow


    fun onAction(action: MoviesListUiActions) {
        when (action) {
            is MoviesListUiActions.getMovies -> {
                useCase.invoke()
            }

            is MoviesListUiActions.addFavorite -> {
                viewModelScope.launch {
                    useCase.addFavorite(action.id)
                }
            }

            is MoviesListUiActions.removeFavorite -> {
                viewModelScope.launch {
                    useCase.removeFavorite(action.id)
                }
            }
        }
    }

}