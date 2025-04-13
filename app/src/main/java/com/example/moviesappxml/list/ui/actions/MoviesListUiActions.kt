package com.example.moviesappxml.list.ui.actions

sealed interface MoviesListUiActions {
    data object getMovies : MoviesListUiActions
    data class addFavorite(val id: Int) : MoviesListUiActions
    data class removeFavorite(val id: Int) : MoviesListUiActions
}