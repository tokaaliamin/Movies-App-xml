package com.example.moviesappxml.list.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.moviesappxml.list.ui.models.Movie

object MovieComparator : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}