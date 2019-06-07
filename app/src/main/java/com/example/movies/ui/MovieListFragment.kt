package com.example.movies.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.movies.R
import com.example.movies.databinding.FragmentMovieListBinding
import com.example.movies.viewmodels.MovieListViewModel

class MovieListFragment : Fragment() {

    /**
     * Lazily initialize  [MovieListViewModel]
     */
    private val viewModel: MovieListViewModel by lazy {
        ViewModelProviders.of(this).get(MovieListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentMovieListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_movie_list, container, false
        )

        return binding.root
    }


}
