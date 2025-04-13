package com.example.moviesappxml.details.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappxml.details.domain.models.MovieDetails
import com.example.moviesappxml.details.domain.models.toUiMovieDetails
import com.example.moviesappxml.details.domain.useCases.MovieDetailsUseCase
import com.example.moviesappxml.details.ui.actions.MovieDetailsUiActions
import com.example.moviesappxml.details.ui.states.DetailsUiState
import com.example.moviesappxml.utils.ErrorParser.parseThrowable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val useCase: MovieDetailsUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState

    fun onAction(action: MovieDetailsUiActions) {
        when (action) {
            is MovieDetailsUiActions.GetMovieDetails -> {
                _uiState.update { it.copy(isLoading = true) }
                getMovieDetails(action.movieId)
            }

            is MovieDetailsUiActions.RefreshMovieDetails -> {
                _uiState.update { it.copy(isRefreshing = true) }
                getMovieDetails(action.movieId)
            }
        }
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val result = useCase.invoke(movieId)
            handleMoviesListResult(result)
        }
    }

    private fun handleMoviesListResult(result: Result<MovieDetails>) {
        when {
            result.isSuccess -> {
                result.getOrNull()?.toUiMovieDetails()
                    ?.apply {
                        _uiState.update {
                            it.copy(
                                movie = this,
                                isRefreshing = false,
                                isLoading = false,
                                errorModel = null
                            )
                        }
                    }
            }

            result.isFailure -> {
                result.exceptionOrNull()?.let { throwable ->
                    _uiState.update {
                        it.copy(
                            errorModel = parseThrowable(throwable),
                            isRefreshing = false,
                            isLoading = false,
                            movie = null
                        )
                    }
                }

            }
        }

    }
}