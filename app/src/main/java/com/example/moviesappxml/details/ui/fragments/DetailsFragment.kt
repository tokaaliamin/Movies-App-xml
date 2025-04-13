package com.example.moviesappxml.details.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.moviesappxml.R
import com.example.moviesappxml.common.ui.models.ErrorModel
import com.example.moviesappxml.common.ui.models.getUserFriendlyErrorMessage
import com.example.moviesappxml.databinding.FragmentDetailsBinding
import com.example.moviesappxml.details.ui.actions.MovieDetailsUiActions
import com.example.moviesappxml.details.ui.models.MovieDetails
import com.example.moviesappxml.details.ui.viewmodels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat


/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var movieId: Int = -1

    val viewModel: DetailsViewModel by viewModels<DetailsViewModel>()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(ARG_MOVIE_ID, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        setupViews()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    binding.progress.isVisible = uiState.isLoading
                    binding.swipeRefresh.isRefreshing = uiState.isRefreshing
                    binding.layoutMovieDetailsData.root.isVisible = uiState.movie != null
                    binding.layoutError.root.isVisible = uiState.errorModel != null

                    uiState.movie?.let { movie ->
                        bindData(movie)
                    }
                    uiState.errorModel?.let { error ->
                        handleError(error)
                    }
                }


            }
        }

        fetchData()

        return view
    }

    private fun setupViews() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        with(binding.toolbar) {
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }

        with(binding.swipeRefresh) {
            setOnRefreshListener {
                refreshData()
            }
        }

        with(binding.layoutError.btnRetry) {
            setOnClickListener {
                fetchData()
            }
        }
    }

    private fun fetchData() {
        viewModel.onAction(MovieDetailsUiActions.GetMovieDetails(movieId))
    }

    private fun refreshData() {
        viewModel.onAction(MovieDetailsUiActions.GetMovieDetails(movieId))
    }

    private fun bindData(movie: MovieDetails) {
        val (hours, mins) = getRunningTime(movie.runtime ?: 0)
        with(binding.layoutMovieDetailsData) {
            tvTitle.text = movie.title
            tvRate.text = DecimalFormat("#.##").format(movie.rating)
            tvRuntime.text = getString(R.string.format_runtime, hours, mins)
            tvOverview.text = movie.description
            tvGenre.text = movie.genres?.map { it.name }?.joinToString(", ")
            Glide.with(requireContext())
                .load(movie.backdropUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .centerCrop()
                .into(ivBackdrop)

            Glide.with(requireContext())
                .load(movie.posterUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .centerCrop().into(ivPoster)
        }
    }

    private fun getRunningTime(runtime: Int) =
        Pair(runtime / 60, runtime % 60)

    private fun handleError(error: ErrorModel) {
        val message = error.getUserFriendlyErrorMessage(resources)
        binding.layoutError.tvErrorMessage.text = message
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: Int) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_MOVIE_ID, movieId)
                }
            }

        const val ARG_MOVIE_ID = "movie_id"
    }
}