package com.example.moviesappxml.details.ui.states

import com.example.moviesappxml.common.ui.models.ErrorModel
import com.example.moviesappxml.details.ui.models.MovieDetails


data class DetailsUiState(
    var movie: MovieDetails? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorModel: ErrorModel? = null
)