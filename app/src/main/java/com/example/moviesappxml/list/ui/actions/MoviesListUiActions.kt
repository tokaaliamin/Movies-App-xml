package com.example.moviesappxml.list.ui.actions

sealed interface MoviesListUiActions {
    data object getMovies : MoviesListUiActions
}