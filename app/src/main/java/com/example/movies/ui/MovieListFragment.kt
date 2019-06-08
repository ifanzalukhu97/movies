package com.example.movies.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.movies.databinding.FragmentMovieListBinding
import com.example.movies.util.MovieClick
import com.example.movies.util.MovieListAdapter
import com.example.movies.viewmodels.MovieListViewModel

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

        return binding.root
    }
}
