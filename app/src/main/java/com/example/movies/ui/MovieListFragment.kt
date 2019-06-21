package com.example.movies.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.movies.R
import com.example.movies.databinding.FragmentMovieListBinding
import com.example.movies.util.MovieClick
import com.example.movies.util.MovieListAdapter
import com.example.movies.viewmodels.MovieListViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*

class MovieListFragment : Fragment() {

    /**
     * Lazily initialize  [MovieListViewModel]
     */
    private val viewModel: MovieListViewModel by lazy {
        val activity = requireNotNull(this.activity)

        ViewModelProviders.of(this, MovieListViewModel.Factory(activity.application))
            .get(MovieListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentMovieListBinding = FragmentMovieListBinding
            .inflate(inflater, container, false)

        binding.lifecycleOwner = this

        val adapter = MovieListAdapter(MovieClick { movie ->
            viewModel.showMovieDetails(movie)
        })

        binding.rvMovies.adapter = adapter

        // Observe movies list in View Model and submit it to Movies RecyclerView adapter
        viewModel.movies.observe(this, Observer { movies ->
            movies?.let {
                adapter.submitList(movies)
            }
        })

        viewModel.navigateToSelectedMovie.observe(this, Observer { movie ->
            if (movie != null) {
                this.findNavController()
                    .navigate(MovieListFragmentDirections.actionMovieListToDetailsMovie(movie))

                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.showMovieDetailsComplete()
            }
        })

        // Get list movies categories from view model and create chip categories filter from the list
        createChipsMoviesFilter(binding.categoryList, viewModel.moviesCategoriesFilter)

        binding.searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onMovieSearch(query)

                // Reset the text query after show result movies
                binding.searchMovie.apply {
                    setQuery("", false)
                    clearFocus()
                    isIconified = true
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })



        return binding.root
    }

    /**
     * @param chipGroup Chip Group for inflate category chip
     * @param categories list of movie category
     */
    private fun createChipsMoviesFilter(chipGroup: ChipGroup, categories: ArrayList<String>) {
        val inflator = LayoutInflater.from(chipGroup.context)

        val categoriesMovie = categories.map { category ->
            val chip = inflator.inflate(R.layout.category, chipGroup, false) as Chip
            chip.apply {
                text = category
                tag = category
                setOnCheckedChangeListener { button, isChecked ->
                    viewModel.onCategoryFilterChanged(button.text as String, isChecked)
                }
            }
            chip
        }

        // Clear all Chip in Chip Group before append new Chip
        chipGroup.removeAllViews()

        // Append all Category Chip in Chip Group
        for (category in categoriesMovie) {
            chipGroup.addView(category)
        }

    }

}
