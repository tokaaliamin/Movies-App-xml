package com.example.moviesappxml.list.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.moviesappxml.databinding.ItemMovieBinding
import com.example.moviesappxml.list.ui.models.Movie
import com.example.moviesappxml.list.ui.viewholders.MovieViewHolder

class MovieAdapter(
    diffCallback: DiffUtil.ItemCallback<Movie>,
    private val onMovieClick: (movieId: Int) -> Unit,
    private val onFavoriteToggleClick: (movieId: Int, isFavorite: Boolean) -> Unit
) : PagingDataAdapter<Movie, MovieViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            parent.context,
            onMovieClick,
            onFavoriteToggleClick
        )

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }
}