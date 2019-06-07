package com.example.movies.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.movies.databinding.FragmentMovieListBinding
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

        val adapter = MovieListAdapter()
        binding.rvMovies.adapter = adapter

        // Observe movies list in View Model and submit it to Movies RecyclerView adapter
        viewModel.movies.observe(this, Observer { movies ->
            movies?.let {
                adapter.submitList(movies)
            }
        })

        return binding.root
    }
}
