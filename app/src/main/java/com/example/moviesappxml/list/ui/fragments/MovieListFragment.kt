package com.example.moviesappxml.list.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.moviesappxml.R
import com.example.moviesappxml.databinding.FragmentMovieListBinding
import com.example.moviesappxml.details.ui.fragments.DetailsFragment
import com.example.moviesappxml.list.ui.actions.MoviesListUiActions
import com.example.moviesappxml.list.ui.adapters.MovieAdapter
import com.example.moviesappxml.list.ui.adapters.MovieComparator
import com.example.moviesappxml.list.ui.adapters.MovieLoadStateAdapter
import com.example.moviesappxml.list.ui.viewmodels.MoviesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class MovieListFragment : Fragment(), MenuProvider {

    private var isGrid = true
    val viewModel: MoviesListViewModel by viewModels<MoviesListViewModel>()

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set up the toolbar as the support action bar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        initializeList()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return view
    }

    private fun initializeList() {
        setColumnCount()
        val pagingAdapter = MovieAdapter(
            MovieComparator,
            onMovieClick = { id ->
                binding.root.findNavController().navigate(
                    R.id.action_list_to_details,
                    Bundle().apply { putInt(DetailsFragment.ARG_MOVIE_ID, id) })
            }, onFavoriteToggleClick = { id, isFavorite ->
                when (isFavorite) {
                    true -> viewModel.onAction(MoviesListUiActions.addFavorite(id))
                    false -> viewModel.onAction(MoviesListUiActions.removeFavorite(id))
                }
            })
        binding.list.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter({ getMovies() }),
            footer = MovieLoadStateAdapter({ getMovies() })
        )

        lifecycleScope.launch {
            viewModel.moviesListFlow.collectLatest { pagingData ->
                if (binding.swipeRefresh.isRefreshing)
                    binding.swipeRefresh.isRefreshing = false
                pagingAdapter.submitData(pagingData)
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            pagingAdapter.refresh()
        }

        getMovies()
    }

    private fun getMovies() {
        viewModel.onAction(MoviesListUiActions.getMovies)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_movie_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.columnCount -> {
                isGrid = isGrid.not()
                changeMenuIcon(menuItem)
                setColumnCount()
            }
        }
        return false
    }

    private fun setColumnCount() {
        with(binding.list) {
            layoutManager = when (isGrid) {
                false -> LinearLayoutManager(context)
                true -> StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }
        }
    }

    private fun changeMenuIcon(menuItem: MenuItem) {
        context?.let { c ->
            val icon = when (isGrid) {
                true -> ContextCompat.getDrawable(c, R.drawable.ic_grid)
                else -> ContextCompat.getDrawable(c, R.drawable.ic_list)
            }
            menuItem.setIcon(icon)
        }
    }

}