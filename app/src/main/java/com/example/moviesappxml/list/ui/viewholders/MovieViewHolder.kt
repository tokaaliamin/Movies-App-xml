package com.example.moviesappxml.list.ui.viewholders

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesappxml.databinding.ItemMovieBinding
import com.example.moviesappxml.list.ui.models.Movie

class MovieViewHolder(
    binding: ItemMovieBinding,
    private val context: Context,
    private val onMovieClick: (movieId: Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    private val posterView: ImageView = binding.ivPoster
    private val titleView: TextView = binding.tvTitle
    private val releaseDateView: TextView = binding.tvReleaseDate

    private var selectedMovie: Movie? = null

    init {
        binding.root.setOnClickListener {
            selectedMovie?.id?.let { id ->
                onMovieClick(id)
            }
        }
    }

    fun bind(item: Movie) {
        selectedMovie = item
        titleView.text = item.title
        Glide.with(context).load(item.posterUrl).centerCrop().into(posterView)
        releaseDateView.text = item.releaseDate
    }
}
