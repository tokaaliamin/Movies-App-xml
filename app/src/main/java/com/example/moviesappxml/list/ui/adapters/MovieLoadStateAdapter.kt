package com.example.moviesappxml.list.ui.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.moviesappxml.list.ui.viewholders.MovieLoadStateViewHolder

// Adapter that displays a loading spinner when
// state is LoadState.Loading, and an error message and retry
// button when state is LoadState.Error.
class MovieLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MovieLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = MovieLoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: MovieLoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}